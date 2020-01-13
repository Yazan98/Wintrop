package com.yazan98.wintrop.client.fragments

import android.view.View
import com.yazan98.wintrop.client.R
import io.vortex.android.ui.fragment.VortexBaseFragment
import javax.inject.Inject

class DestinationFragment @Inject constructor() : VortexBaseFragment() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_destination
    }

    override fun initScreen(view: View) {

    }

}
