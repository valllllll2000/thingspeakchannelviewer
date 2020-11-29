package com.vaxapp.thingspeakviewer

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.vaxapp.thingspeakviewer.di.officeWeatherAppModules
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin

class OfficeWeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, officeWeatherAppModules())
        val crashlyticsCore = CrashlyticsCore.Builder()
                .disabled(true)
                .build()
        Fabric.with(this, Crashlytics.Builder().core(crashlyticsCore).build())
    }
}
