package com.vaxapp.thingspeakviewer.di

import com.vaxapp.thingspeakviewer.domain.GetOfficeWeatherUseCase
import com.vaxapp.thingspeakviewer.domain.OfficeWeatherRepository
import org.koin.dsl.module

val domainModule = module {
    single { provideGetOfficeUseCase(get()) }
}

fun provideGetOfficeUseCase(repository: OfficeWeatherRepository): GetOfficeWeatherUseCase {
    return GetOfficeWeatherUseCase(repository)
}
