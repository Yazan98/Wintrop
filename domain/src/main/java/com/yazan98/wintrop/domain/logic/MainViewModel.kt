package com.yazan98.wintrop.domain.logic

import com.yazan98.wintrop.data.database.dao.ConditionDao
import com.yazan98.wintrop.data.di.DatabaseComponentImpl
import com.yazan98.wintrop.data.di.RepositoriesComponentImpl
import com.yazan98.wintrop.domain.action.MainAction
import com.yazan98.wintrop.domain.state.MainState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor() : VortexViewModel<MainState , MainAction>() {

    private val jordanRepository by lazy {
        RepositoriesComponentImpl().getJordanRepository()
    }

    private val databaseManager: ConditionDao by lazy {
        DatabaseComponentImpl().getConditionDao()
    }

    override suspend fun getInitialState(): MainState {
        return MainState.EmptyState()
    }

    override suspend fun reduce(newAction: MainAction) {
        withContext(Dispatchers.IO) {
            if(getStateHandler().value == null || getStateHandler().value is MainState.ErrorState) {
                when (newAction) {
                    is MainAction.ClearDatabase -> clearDatabase()
                    is MainAction.GetWeatherInfoByCityName -> {
                        newAction.get()?.let {
                            getCityByName(it)
                        }
                    }
                }
            }
        }
    }

    private suspend fun getCityByName(name: String) {
        // At this case Amman Just Here i will handle just amman process
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(jordanRepository.getAmmanStatus().subscribe({
                it.let {
                    GlobalScope.launch {
                        handleSuccessState(MainState.SuccessResponse(it))
                    }
                }
            } , {
                it?.let {
                    it.message?.let {
                        GlobalScope.launch {
                            acceptLoadingState(false)
                            acceptNewState(MainState.ErrorState(it))
                        }
                    }
                }
            }))
        }
    }

    private suspend fun clearDatabase() {
        withContext(Dispatchers.IO) {
            databaseManager.clearConditions()
        }
    }

    override fun onCleared() {
        super.onCleared()
        GlobalScope.launch {
            destroyReducer()
        }
    }

}
