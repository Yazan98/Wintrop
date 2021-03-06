package com.yazan98.wintrop.client.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yazan98.wintrop.client.R
import com.yazan98.wintrop.client.adapters.DaysAdapter
import com.yazan98.wintrop.client.adapters.MonthsAdapter
import com.yazan98.wintrop.client.utils.Utils
import com.yazan98.wintrop.data.models.MonthResponse
import com.yazan98.wintrop.data.models.Weather
import com.yazan98.wintrop.data.models.WeatherResponse
import com.yazan98.wintrop.domain.action.MainAction
import com.yazan98.wintrop.domain.logic.MainViewModel
import com.yazan98.wintrop.domain.state.MainState
import io.vortex.android.ui.fragment.VortexFragment
import io.vortex.android.utils.ui.hideView
import io.vortex.android.utils.ui.showView
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel
import javax.inject.Inject

class MainFragment @Inject constructor() : VortexFragment<MainState, MainAction, MainViewModel>(),
    SettingsFragment.SettingsClick {

    private val viewModel: MainViewModel by viewModel<MainViewModel>()
    private val settingsDialog: SettingsFragment by lazy {
        SettingsFragment()
    }

    override fun initScreen(view: View) {
        MainToolbar?.apply {
            activity?.let {
                (it as AppCompatActivity).setSupportActionBar(this)
                it.supportActionBar?.title = getString(R.string.default_title)
                this.setNavigationIcon(R.drawable.ic_menu)
            }

            this.setNavigationOnClickListener {
                showSettingsFragment()
            }
        }

        GlobalScope.launch {
            subscribeObservers()
            getController().reduce(MainAction.GetWeatherInfoByCityName("Amman"))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsDialog.attachListener(this)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_main
    }

    override suspend fun getController(): MainViewModel {
        return viewModel
    }

    private suspend fun subscribeObservers() {
        withContext(Dispatchers.Main) {
            getController().getStateHandler().observe(this@MainFragment, Observer {
                GlobalScope.launch {
                    onStateChanged(it)
                }
            })

            getController().getLoadingStateHandler().observe(this@MainFragment, Observer {
                GlobalScope.launch {
                    changeLoading(it.getLoadingState())
                }
            })
        }
    }

    private suspend fun changeLoading(status: Boolean) {
        withContext(Dispatchers.Main) {
            when (status) {
                true -> {
                    MainLoading?.showView()
                    ErrorView?.hideView()
                    Container?.hideView()
                }

                false -> {
                    MainLoading?.hideView()
                   getController().getStateHandler().value?.let {
                       if (it !is MainState.ErrorState) {
                           Container?.showView()
                           ErrorView?.hideView()
                       }
                   }
                }
            }
        }
    }

    override suspend fun onStateChanged(newState: MainState) {
        withContext(Dispatchers.IO) {
            when (newState) {
                is MainState.ErrorState -> showMessage(newState.get())
                is MainState.SuccessResponse -> loadResponse(newState.get())
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private suspend fun loadResponse(response: WeatherResponse) {
        withContext(Dispatchers.Main) {
            /**
             * Always this arrays return one object always index 0 is valid and always the Array us just one item :D
             */
            MainTitle?.let { it.text = "${response.data.request[0].query}" }
            response.data.currentConditions[0].let { condition ->
                DesTitleF?.let { it.text = "${condition.tempFe}${getString(R.string.present)}" }
                CardDescription?.let { it.text = condition.description[0].value }
                SpeedWindsView?.let { it.text = "${getString(R.string.speed_per_hour)} ${condition.windSpeedPerHours}" }
                WindDegree?.let { it.text = "${getString(R.string.wind_degree)} ${condition.windDegree}" }
                CardImage?.let { it.setImageResource(Utils.getImageByStatus(condition.description[0].value)) }
            }

            displayComingDays(response.data.weather)
            displayComingMonths(response.data.avarage[0].month)
        }
    }

    private suspend fun displayComingMonths(data: List<MonthResponse>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                MainMonthRecycler?.apply {
                    this.layoutManager = LinearLayoutManager(it , LinearLayoutManager.VERTICAL , false)
                    this.adapter = MonthsAdapter(data)
                    (this.adapter as MonthsAdapter).context = it
                }
            }
        }
    }

    private suspend fun displayComingDays(data: List<Weather>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                MainRecycler?.apply {
                    this.layoutManager = LinearLayoutManager(it , LinearLayoutManager.HORIZONTAL , false)
                    this.adapter = DaysAdapter(data)
                    (this.adapter as DaysAdapter).context = it
                }
            }
        }
    }

    private suspend fun showMessage(message: String) {
        withContext(Dispatchers.Main) {
            activity?.let {
                messageController.showSnackbar(it, message)
            }
        }
    }

    private fun showSettingsFragment() {
        activity?.let { it ->
            it.supportFragmentManager.let {
                settingsDialog.show(it, "SettingsFragment")
            }
        }
    }

    override fun onOptionChoose(index: Int) {
        GlobalScope.launch {
            when (index) {
                1 -> startNewNameAction("Irbid")
                2 -> startNewNameAction("Aqaba")
            }
        }
    }

    private suspend fun startNewNameAction(name: String) {
        withContext(Dispatchers.IO) {
            getController().reduce(MainAction.GetWeatherInfoByCityName(name, true))
        }
    }

    override fun onDestroy() {
        settingsDialog.destroyListener()
        super.onDestroy()
    }


}
