package com.yazan98.wintrop.domain.state

import io.vortex.android.state.VortexState

open class MainState : VortexState {

    class EmptyState: MainState()
    class ErrorState(private val message: String): MainState()

}