package com.vaxapp.thingspeak.viewer.data.weather

import com.vaxapp.thingspeak.viewer.domain.DomainResponse
import com.vaxapp.thingspeak.viewer.domain.OfficeWeatherRepository
import io.reactivex.Single

class DataOfficeWeatherRepository(
    private val dataSource: OfficeWeatherCloudDataSource,
    private val mapper: DomainResponseMapper
) : OfficeWeatherRepository {

    override fun getOfficeWeather(): Single<DomainResponse> {
        return dataSource.fetchWeather().map {
            mapper.toDomainResponse(it)
        }
    }
}
