package com.vaxapp.thingspeakviewer.di

import android.content.Context
import com.vaxapp.thingspeakviewer.data.ApiService
import com.vaxapp.thingspeakviewer.data.DataOfficeWeatherRepository
import com.vaxapp.thingspeakviewer.data.OfficeWeatherCloudDataSource
import com.vaxapp.thingspeakviewer.domain.OfficeWeatherRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.applicationContext


val dataModule = applicationContext {
    bean { provideOfficeWeatherRepository(get()) }
    bean { provideOfficeWeatherDataSource(get()) }
    bean { provideApiService(androidContext()) }
}

fun provideApiService(context: Context): ApiService {
    return ApiService.create(context)
}

fun provideOfficeWeatherDataSource(get: ApiService): OfficeWeatherCloudDataSource {
    return OfficeWeatherCloudDataSource(get)
}

fun provideOfficeWeatherRepository(dataSource: OfficeWeatherCloudDataSource): OfficeWeatherRepository {
    return DataOfficeWeatherRepository(dataSource)
}
