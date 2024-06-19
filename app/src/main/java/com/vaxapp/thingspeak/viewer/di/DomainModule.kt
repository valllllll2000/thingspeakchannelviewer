package com.vaxapp.thingspeak.viewer.di

import com.vaxapp.thingspeak.viewer.domain.GetOfficeWeatherUseCase
import com.vaxapp.thingspeak.viewer.domain.OfficeWeatherRepository
import org.koin.dsl.module

val domainModule = module {
    single { provideGetOfficeUseCase(get()) }
}

fun provideGetOfficeUseCase(repository: OfficeWeatherRepository): GetOfficeWeatherUseCase {
    return GetOfficeWeatherUseCase(repository)
}
