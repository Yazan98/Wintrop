package com.yazan98.wintrop.client.screens

import android.os.Bundle
import com.yazan98.wintrop.client.R
import io.vortex.android.ui.activity.VortexScreen
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashScreen : VortexScreen() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ExploreButton?.apply {
            this.setOnClickListener {
                GlobalScope.launch {
                    startScreen<MainScreen>(true)
                }
            }
        }
    }

}
