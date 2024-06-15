package com.vaxapp.thingspeakviewer.di

import com.vaxapp.thingspeakviewer.view.main.MainPresenter
import com.vaxapp.thingspeakviewer.view.main.ViewResponseMapper
import org.koin.core.module.Module
import org.koin.dsl.module
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

val viewModule: Module = module {
    single { MainPresenter(get(), get()) }
    single { provideGetViewResponseMapper() }
}

fun provideGetViewResponseMapper(): ViewResponseMapper {
    val serverDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    serverDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val localDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
    localDateFormat.timeZone = TimeZone.getDefault()
    return ViewResponseMapper(serverDateFormat, localDateFormat)
}
