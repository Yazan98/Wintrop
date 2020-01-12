package com.yazan98.wintrop.domain.logic

import com.yazan98.wintrop.domain.action.MainAction
import com.yazan98.wintrop.domain.state.MainState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor() : VortexViewModel<MainState , MainAction>() {


    override suspend fun getInitialState(): MainState {
        return MainState.EmptyState()
    }

    override suspend fun reduce(newAction: MainAction) {
        withContext(Dispatchers.IO) {

        }
    }

}