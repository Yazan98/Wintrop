package com.yazan98.wintrop.domain.action

import io.vortex.android.VortexAction

open class MainAction : VortexAction {
    class GetWeatherInfoByCityName(private val name: String?, private val isNew: Boolean = false): MainAction() {
        fun get() = name
        fun getStatus() = isNew
    }
}