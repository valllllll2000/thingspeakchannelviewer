package com.vaxapp.thingspeak.viewer.di

import android.content.Context
import com.vaxapp.thingspeak.viewer.data.ApiService
import com.vaxapp.thingspeak.viewer.data.weather.DataOfficeWeatherRepository
import com.vaxapp.thingspeak.viewer.data.weather.DomainResponseMapper
import com.vaxapp.thingspeak.viewer.data.weather.OfficeWeatherCloudDataSource
import com.vaxapp.thingspeak.viewer.domain.OfficeWeatherRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

val dataModule: Module = module {
    single { provideDomainResponseMapper() }
    single { provideOfficeWeatherRepository(get(), get()) }
    single { provideOfficeWeatherDataSource(get()) }
    single { provideApiService(androidContext()) }
}

fun provideApiService(context: Context): ApiService {
    return ApiService.create(context)
}

fun provideOfficeWeatherDataSource(get: ApiService): OfficeWeatherCloudDataSource {
    return OfficeWeatherCloudDataSource(get)
}

fun provideDomainResponseMapper(): DomainResponseMapper {
    val serverDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    serverDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return DomainResponseMapper(serverDateFormat)
}

fun provideOfficeWeatherRepository(
    dataSource: OfficeWeatherCloudDataSource,
    domainResponseMapper: DomainResponseMapper
): OfficeWeatherRepository {
    return DataOfficeWeatherRepository(dataSource, domainResponseMapper)
}
