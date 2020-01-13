package com.yazan98.wintrop.domain.logic

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

class DestinationViewModel @Inject constructor() : VortexViewModel<DestinationState, DestinationAction>() {

    private val repository: JordanRepository by lazy {
        RepositoriesComponentImpl().getJordanRepository()
    }

    override suspend fun getInitialState(): DestinationState {
        return DestinationState.EmptyState()
    }

    override suspend fun reduce(newAction: DestinationAction) {
        withContext(Dispatchers.IO) {
            when {
                (newAction as DestinationAction.GetStatusByName).get().isEmpty() -> {}
                (true) -> getWeatherStatusByName(newAction.get())
            }
        }
    }

    private suspend fun getWeatherStatusByName(name: String) {
        withContext(Dispatchers.IO) {

        }
    }

    private suspend fun getStatusByCityName(name: String) {
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(repository.getService().getWeatherStatusByCityName(query = name)
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

    override fun onCleared() {
        super.onCleared()
        GlobalScope.launch {
            destroyReducer()
        }
    }

}
