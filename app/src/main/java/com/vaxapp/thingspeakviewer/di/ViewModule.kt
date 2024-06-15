package com.vaxapp.thingspeakviewer.di

import com.vaxapp.thingspeakviewer.view.NotificationHelper
import com.vaxapp.thingspeakviewer.view.main.MainPresenter
import com.vaxapp.thingspeakviewer.view.main.ViewResponseMapper
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.module.Module
import org.koin.dsl.module
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

val viewModule: Module = module {
    single { NotificationHelper() }
    single { coroutineContext() }
    single { MainPresenter(get(), get(), get(), get()) }
    single { provideGetViewResponseMapper() }
}

private fun coroutineContext() = Job() + Dispatchers.Main + CoroutineName("MainPresenter")

fun provideGetViewResponseMapper(): ViewResponseMapper {
    val localDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
    localDateFormat.timeZone = TimeZone.getDefault()
    return ViewResponseMapper(localDateFormat)
}
