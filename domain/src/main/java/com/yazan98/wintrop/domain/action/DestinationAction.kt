package com.yazan98.wintrop.domain.action

import io.vortex.android.VortexAction

open class DestinationAction: VortexAction {
    class GetStatusByName(private val name: String): DestinationAction() {
        fun get() = name
    }
}