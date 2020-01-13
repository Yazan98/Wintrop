package com.yazan98.wintrop.domain.logic

import com.yazan98.wintrop.data.ConditionMapper
import com.yazan98.wintrop.data.database.dao.ConditionDao
import com.yazan98.wintrop.data.di.DatabaseComponentImpl
import com.yazan98.wintrop.data.di.RepositoriesComponentImpl
import com.yazan98.wintrop.data.repository.JordanRepository
import com.yazan98.wintrop.domain.action.DestinationAction
import com.yazan98.wintrop.domain.state.DestinationState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DestinationViewModel @Inject constructor() :
    VortexViewModel<DestinationState, DestinationAction>() {

    private val repository: JordanRepository by lazy {
        RepositoriesComponentImpl().getJordanRepository()
    }

    private val database: ConditionDao by lazy {
        DatabaseComponentImpl().getConditionDao()
    }

    private val responseMapper: ConditionMapper by lazy {
        ConditionMapper()
    }

    override suspend fun getInitialState(): DestinationState {
        return DestinationState.EmptyState()
    }

    override suspend fun reduce(newAction: DestinationAction) {
        withContext(Dispatchers.IO) {
            when {
                (newAction as DestinationAction.GetStatusByName).get().isEmpty() -> acceptNewState(
                    DestinationState.ErrorState("City Name Required")
                )
                (true) -> getWeatherStatusByName(newAction.get())
            }
        }
    }

    private suspend fun getWeatherStatusByName(name: String) {
        withContext(Dispatchers.IO) {
            try {
                when (database.getConditionsNumber()) {
                    0 -> getStatusByCityName(name, true)
                    else -> {
                        handleMultiCityEntity(name)
                    }
                }
            } catch (ex: Exception) {
                ex.message?.let {
                    GlobalScope.launch {
                        acceptNewState(DestinationState.ErrorState(it))
                    }
                }
            }
        }
    }

    private suspend fun getStatusByCityName(name: String, loading: Boolean) {
        withContext(Dispatchers.IO) {
            acceptLoadingState(loading)
            addRxRequest(
                repository.getService().getWeatherStatusByCityName(query = name)
                    .subscribe({
                        it.data.let {
                            it.currentConditions[0].let {
                                GlobalScope.launch {
                                    handleSuccessState(DestinationState.SuccessState(it))
                                }
                            }
                        }
                    }, {
                        it?.let {
                            it.message?.let {
                                GlobalScope.launch {
                                    acceptLoadingState(false)
                                    acceptNewState(DestinationState.ErrorState(it))
                                }
                            }
                        }
                    })
            )
        }
    }

    private suspend fun handleMultiCityEntity(name: String) {
        withContext(Dispatchers.IO) {
            database.getConditionByCityName(name).value?.let {
                it.first()?.let { result ->
                    acceptNewState(DestinationState.SuccessState(responseMapper.from(result)))
                    getStatusByCityName(name, false)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        GlobalScope.launch {
            destroyReducer()
        }
    }

}
