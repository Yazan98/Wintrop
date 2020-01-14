package com.yazan98.wintrop.domain.logic

import com.yazan98.wintrop.data.database.DatabaseManager
import com.yazan98.wintrop.data.di.RepositoriesComponentImpl
import com.yazan98.wintrop.data.models.WeatherResponse
import com.yazan98.wintrop.domain.action.MainAction
import com.yazan98.wintrop.domain.state.MainState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainViewModel @Inject constructor() : VortexViewModel<MainState, MainAction>() {

    private val jordanRepository by lazy {
        RepositoriesComponentImpl().getJordanRepository()
    }

    override suspend fun getInitialState(): MainState {
        return MainState.EmptyState()
    }

    override suspend fun reduce(newAction: MainAction) {
        withContext(Dispatchers.IO) {
            if (getStateHandler().value == null || getStateHandler().value is MainState.ErrorState) {
                when (newAction) {
                    is MainAction.GetWeatherInfoByCityName -> {
                        println("Main Flow : Start Task First Time")
                        executeWeatherTask(newAction)
                    }
                }
            } else {
                (newAction as MainAction.GetWeatherInfoByCityName).let {
                    if (it.getStatus()) {
                        println("Main Flow : Start Task With True ReLoad")
                        executeWeatherTask(newAction)
                    }
                }
            }
        }
    }

    private suspend fun executeWeatherTask(newAction: MainAction.GetWeatherInfoByCityName) {
        withContext(Dispatchers.IO) {
            newAction.get()?.let {
                DatabaseManager.getAllConditions(it).let { result ->
                    if (result.isNotEmpty()) {
                        fetchFromDatabase(it)
                        getCityByName(it, false)
                    } else {
                        getCityByName(it, true)
                    }
                }
            }
        }

    }

    private suspend fun getCityByName(name: String, loading: Boolean) {
        withContext(Dispatchers.IO) {
            acceptLoadingState(loading)
            addRxRequest(
                jordanRepository.getService().getWeatherStatusByCityName(query = name).subscribe(
                    {
                        it.let {
                            GlobalScope.launch {
                                handleSuccessState(MainState.SuccessResponse(it))
                                insertIntoDatabase(it)
                            }
                        }
                    },
                    {
                        it?.let {
                            it.message?.let {
                                GlobalScope.launch {
                                    acceptLoadingState(false)
                                    acceptNewState(MainState.ErrorState(it))
                                }
                            }
                        }
                    })
            )
        }
    }

    private suspend fun insertIntoDatabase(response: WeatherResponse) {
        withContext(Dispatchers.IO) {
            val result: WeatherResponse = if(response.data.request[0].query.contains("Aqaba")) {
                getResponseFromAqaba(response)
            } else {
                getNormalResponse(response)
            }
            DatabaseManager.insertIntoDatabase(result)
        }
    }

    /**
     * Here There is A Huge Problem With Aqaba Specially because Aqaba At Response Like This
     * "request": [
    {
    "type": "City",
    "query": "`Aqaba, Jordan"
    }
    ]
    There is a comma Before Aqaba and im Passing Just Aqaba not with comma
    For THis Reason im Splitting each Name To Get The City Name Without any comma
     */
    private suspend fun getResponseFromAqaba(response: WeatherResponse) = suspendCoroutine<WeatherResponse> {
        response.data.weather?.forEach {
            val name = response.data.request[0].query.split(",")[0]
            it.cityName = name.substring(1, name.length)
        }
        response.data.avarage[0].month.forEach {
            val name = response.data.request[0].query.split(",")[0]
            it.cityName = name.substring(1, name.length)
        }
        val name = response.data.request[0].query.split(",")[0]
        response.data.currentConditions[0].cityName = name.substring(1, name.length)
        it.resume(response)
    }

    private suspend fun getNormalResponse(response: WeatherResponse) = suspendCoroutine<WeatherResponse> {
        response.data.weather?.forEach {
            it.cityName = response.data.request[0].query.split(",")[0]
        }
        response.data.avarage[0].month.forEach {
            it.cityName = response.data.request[0].query.split(",")[0]
        }
        response.data.currentConditions[0].cityName = response.data.request[0].query.split(",")[0]
        it.resume(response)
    }

    private suspend fun fetchFromDatabase(cityName: String) {
        withContext(Dispatchers.IO) {
            acceptNewState(MainState.SuccessResponse(DatabaseManager.getResponseByCityName(cityName)))
        }
    }

    override fun onCleared() {
        super.onCleared()
        GlobalScope.launch {
            destroyReducer()
        }
    }

}
