package com.vaxapp.thingspeakviewer.di

import android.content.Context
import com.vaxapp.thingspeakviewer.data.ApiService
import com.vaxapp.thingspeakviewer.data.DataOfficeWeatherRepository
import com.vaxapp.thingspeakviewer.data.DomainResponseMapper
import com.vaxapp.thingspeakviewer.data.OfficeWeatherCloudDataSource
import com.vaxapp.thingspeakviewer.domain.OfficeWeatherRepository
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
