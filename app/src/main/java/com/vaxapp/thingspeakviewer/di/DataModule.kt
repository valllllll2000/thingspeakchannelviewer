package com.vaxapp.thingspeakviewer.di

import com.vaxapp.thingspeakviewer.data.ApiService
import com.vaxapp.thingspeakviewer.data.DataOfficeWeatherRepository
import com.vaxapp.thingspeakviewer.data.OfficeWeatherCloudDataSource
import com.vaxapp.thingspeakviewer.domain.OfficeWeatherRepository
import org.koin.dsl.module.applicationContext


val dataModule = applicationContext {
    bean { provideOfficeWeatherRepository(get()) }
    bean { provideOfficeWeatherDataSource(get()) }
    bean { provideApiService() }
}

fun provideApiService(): ApiService {
    return ApiService.create();
}

fun provideOfficeWeatherDataSource(get: ApiService): OfficeWeatherCloudDataSource {
    return OfficeWeatherCloudDataSource(get)
}

fun provideOfficeWeatherRepository(dataSource: OfficeWeatherCloudDataSource): OfficeWeatherRepository {
    return DataOfficeWeatherRepository(dataSource)
}
