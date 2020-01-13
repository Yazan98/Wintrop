package com.yazan98.wintrop.domain.state

import com.yazan98.wintrop.data.models.ConditionResponse
import io.vortex.android.state.VortexState

open class DestinationState : VortexState {

    class EmptyState: DestinationState()
    class SuccessState(private val response: ConditionResponse): DestinationState() {
        fun get() = response
    }

    class ErrorState(private val message: String): DestinationState() {
        fun get() = message
    }

}
