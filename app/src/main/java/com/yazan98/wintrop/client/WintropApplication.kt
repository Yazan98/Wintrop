package com.yazan98.wintrop.client

import com.yazan98.wintrop.data.database.DatabaseManager
import com.yazan98.wintrop.data.repository.JordanRepository
import com.yazan98.wintrop.domain.logic.MainViewModel
import io.vortex.android.keys.ImageLoader
import io.vortex.android.keys.LoggerType
import io.vortex.android.models.ui.VortexNotificationDetails
import io.vortex.android.ui.VortexMessageDelegation
import io.vortex.android.utils.VortexApplication
import io.vortex.android.utils.VortexConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber


class WintropApplication : VortexApplication(), Thread.UncaughtExceptionHandler {

    private val messageController: VortexMessageDelegation by lazy {
        VortexMessageDelegation()
    }

    override fun onCreate() {
        super.onCreate()

        GlobalScope.launch {
            VortexConfiguration
                .registerApplicationClass(this@WintropApplication)
                .registerApplicationLogger(LoggerType.TIMBER)
                .registerApplicationState(BuildConfig.DEBUG)
                .registerCompatVector()
                .registerExceptionHandler(this@WintropApplication)
                .registerImageLoader(ImageLoader.FRESCO)
                .registerStrictMode()

            registerNotificationChannels()
        }

        GlobalScope.launch {
           applicationContext?.let {
               DatabaseManager.initDatabase(it)
           }
        }

        startKoin {
            androidLogger()
            androidContext(this@WintropApplication)
            modules(applicationModules)
        }

    }

    private suspend fun registerNotificationChannels() {
        withContext(Dispatchers.IO) {
            applicationContext?.let {
                notificationsController.createMultiNotificationChannels(
                    arrayListOf(
                        VortexNotificationDetails(
                            getString(R.string.notification_channel_name),
                            getString(R.string.notification_channel_des),
                            getString(R.string.notification_channel_id)
                        ),
                        VortexNotificationDetails(
                            getString(R.string.notification_channel_name_news),
                            getString(R.string.notification_channel_des_news),
                            getString(R.string.notification_channel_id_news)
                        )
                    ), it
                )
            }
        }
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        GlobalScope.launch {
            e.let {
                it.message?.apply {
                    Timber.e("THe Un Expected Error is : $this")
                    applicationContext?.let {
                        messageController.showLongMessage(
                            this,
                            it
                        )
                    }
                }
            }
        }
    }

    private val applicationModules = module {
        viewModel { MainViewModel() }
        single { JordanRepository() }
    }

}
