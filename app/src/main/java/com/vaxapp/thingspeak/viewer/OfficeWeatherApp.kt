package com.vaxapp.thingspeak.viewer

import android.app.Application
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.vaxapp.thingspeak.viewer.di.officeWeatherAppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import java.util.concurrent.TimeUnit

class OfficeWeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@OfficeWeatherApp)
            modules(officeWeatherAppModules())
        }

        val constraints = Constraints.Builder()
            .build()

        val downloadRequest = PeriodicWorkRequestBuilder<VerifyChannelDataWorkManager>(
            12, TimeUnit.HOURS // Repeat every 12 hours
        )
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                10, TimeUnit.MINUTES // Backoff policy for retries
            )
            .build()
        WorkManager.getInstance(this).enqueue(downloadRequest)
    }
}
