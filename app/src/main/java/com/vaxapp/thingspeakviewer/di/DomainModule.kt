package com.vaxapp.thingspeakviewer.di

import com.vaxapp.thingspeakviewer.domain.GetOfficeWeatherUseCase
import com.vaxapp.thingspeakviewer.domain.OfficeWeatherRepository
import org.koin.dsl.module.applicationContext


val domainModule = applicationContext {
    bean { provideGetOfficeUseCase(get()) }
}

fun provideGetOfficeUseCase(repository: OfficeWeatherRepository): GetOfficeWeatherUseCase {
    return GetOfficeWeatherUseCase(repository)
}
