package com.vaxapp.thingspeakviewer

import android.app.Application
import com.vaxapp.thingspeakviewer.di.officeWeatherAppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class OfficeWeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@OfficeWeatherApp)
            modules(officeWeatherAppModules())
        }
    }
}
