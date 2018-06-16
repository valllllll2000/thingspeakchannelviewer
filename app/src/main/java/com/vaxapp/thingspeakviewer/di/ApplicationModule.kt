package com.vaxapp.thingspeakviewer.di

import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext


fun officeWeatherAppModules() = listOf(dataModule, domainModule, viewModule, module)

val module: Module = applicationContext {

}