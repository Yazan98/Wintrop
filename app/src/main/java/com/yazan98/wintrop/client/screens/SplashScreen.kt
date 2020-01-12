package com.yazan98.wintrop.client.screens

import android.os.Bundle
import com.yazan98.wintrop.client.R
import io.vortex.android.ui.activity.VortexScreen

class SplashScreen : VortexScreen() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}
