package com.yazan98.wintrop.domain.state

import com.yazan98.wintrop.data.models.WeatherResponse
import io.vortex.android.state.VortexState

open class MainState : VortexState {

    class EmptyState: MainState()
    class ErrorState(private val message: String): MainState() {
        fun get() = message
    }

    class SuccessResponse(private val response: WeatherResponse): MainState() {
        fun get() = response
    }

}
