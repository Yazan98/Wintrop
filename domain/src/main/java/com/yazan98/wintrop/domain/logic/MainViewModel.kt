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
                        println("Main Flow : Task Execute")
                        executeWeatherTask(newAction)
                    }
                }
            }
        }
    }

    private suspend fun executeWeatherTask(newAction: MainAction.GetWeatherInfoByCityName) {
        withContext(Dispatchers.IO) {
            DatabaseManager.getAllConditions().let {
                if (it == null) {
                    println("Main Flow : Null Result")
                    newAction.get()?.let { it1 -> getCityByName(it1, true) }
                } else {
                    println("Main Flow : Get All Let : ${it}")
                    it.isEmpty()?.let { result ->
                        newAction.get()?.let {
                            when (result) {
                                true -> {
                                    getCityByName(it, true)
                                    println("Main Flow : Task Result True")
                                }
                                false -> {
                                    fetchFromDatabase(it)
                                    getCityByName(it, false)
                                    println("Main Flow : Task Result False")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun getCityByName(name: String, loading: Boolean) {
        withContext(Dispatchers.IO) {
            println("Main Flow : Start Request")
            acceptLoadingState(loading)
            addRxRequest(
                jordanRepository.getService().getWeatherStatusByCityName(query = name).subscribe(
                    {
                        it.let {
                            GlobalScope.launch {
                                handleSuccessState(MainState.SuccessResponse(it))
                                insertIntoDatabase(it, name)
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

    private suspend fun insertIntoDatabase(response: WeatherResponse, name: String) {
        withContext(Dispatchers.IO) {
            response.data.weather?.forEach {
                it.cityName = name
            }

            response.data.avarage[0].month.forEach {
                it.cityName = name
            }

            response.data.currentConditions[0].cityName = name
        }
        DatabaseManager.insertIntoDatabase(response)
    }

    private suspend fun fetchFromDatabase(cityName: String) {
        withContext(Dispatchers.IO) {
            println("Main Flow : Start From Database")
            println(
                "Main Flow : Result From Database : ${DatabaseManager.getResponseByCityName(
                    cityName
                )}"
            )
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
