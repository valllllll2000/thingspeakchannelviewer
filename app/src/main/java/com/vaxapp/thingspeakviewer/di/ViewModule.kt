package com.vaxapp.thingspeakviewer.di

import com.vaxapp.thingspeakviewer.view.main.MainPresenter
import com.vaxapp.thingspeakviewer.view.main.ViewResponseMapper
import org.koin.dsl.module.applicationContext
import java.text.SimpleDateFormat
import java.util.*


val viewModule = applicationContext {
    bean { MainPresenter(get(), provideGetViewResponseMapper()) }
}

fun provideGetViewResponseMapper(): ViewResponseMapper {
    val serverDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    serverDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val localDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
    localDateFormat.timeZone = TimeZone.getDefault()
    return ViewResponseMapper(serverDateFormat, localDateFormat)
}
