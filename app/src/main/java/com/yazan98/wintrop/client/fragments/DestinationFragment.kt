package com.yazan98.wintrop.client.fragments

import android.view.View
import androidx.lifecycle.Observer
import com.yazan98.wintrop.client.R
import com.yazan98.wintrop.domain.action.DestinationAction
import com.yazan98.wintrop.domain.logic.DestinationViewModel
import com.yazan98.wintrop.domain.state.DestinationState
import io.vortex.android.ui.fragment.VortexBaseFragment
import io.vortex.android.ui.fragment.VortexFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel
import javax.inject.Inject

class DestinationFragment @Inject constructor() : VortexFragment<DestinationState, DestinationAction, DestinationViewModel>() {

    private val viewModel: DestinationViewModel by viewModel<DestinationViewModel>()

    override suspend fun getController(): DestinationViewModel {
        return viewModel
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_destination
    }

    override fun initScreen(view: View) {
        GlobalScope.launch {
            subscribeObservers()
        }

        arguments?.let {
            it.getString("Name", "")?.also {
                GlobalScope.launch {
                    getController().reduce(DestinationAction.GetStatusByName(it))
                }
            }
        }
    }

    private suspend fun subscribeObservers() {
        withContext(Dispatchers.Main) {
            getController().getStateHandler().observe(this@DestinationFragment, Observer {
                GlobalScope.launch {
                    onStateChanged(it)
                }
            })

            getController().getLoadingStateHandler().observe(this@DestinationFragment, Observer {
                GlobalScope.launch {
                    changeLoading(it.getLoadingState())
                }
            })
        }
    }

    override suspend fun onStateChanged(newState: DestinationState) {
        withContext(Dispatchers.IO) {

        }
    }

    private suspend fun changeLoading(status: Boolean) {
        withContext(Dispatchers.Main) {
            when (status) {
                true -> {}
                false -> {}
            }
        }
    }

}
