package com.vaxapp.thingspeakviewer

import android.app.Application
import com.vaxapp.thingspeakviewer.di.officeWeatherAppModules
import org.koin.android.ext.android.startKoin

class OfficeWeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, officeWeatherAppModules())
    }
}
