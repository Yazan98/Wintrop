package com.yazan98.wintrop.client.fragments

import io.vortex.android.ui.fragment.VortexBaseFragment
import io.vortex.android.ui.fragment.VortexFragment
import javax.inject.Inject

class SearchFragment @Inject constructor() : VortexBaseFragment() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_search
    }
}