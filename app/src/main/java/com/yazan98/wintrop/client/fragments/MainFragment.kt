package com.yazan98.wintrop.client.fragments

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yazan98.wintrop.client.R
import io.vortex.android.ui.fragment.VortexBaseFragment
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment @Inject constructor() : VortexBaseFragment() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_main
    }

    override fun initScreen(view: View) {
        MainToolbar?.apply {
            activity?.let {
                (it as AppCompatActivity).setSupportActionBar(this)
                it.supportActionBar?.title = getString(R.string.empty_text)
                this.setNavigationIcon(R.drawable.ic_menu)
                setHasOptionsMenu(true)
            }

            this.setNavigationOnClickListener {

            }
        }

    }

}
