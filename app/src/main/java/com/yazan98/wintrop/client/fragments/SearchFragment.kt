package com.yazan98.wintrop.client.fragments

import android.view.View
import com.yazan98.wintrop.client.R
import io.vortex.android.ui.fragment.VortexBaseFragment
import io.vortex.android.ui.fragment.VortexFragment
import javax.inject.Inject

class SearchFragment @Inject constructor() : VortexBaseFragment() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_search
    }

    override fun initScreen(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
