package com.vaxapp.thingspeakviewer.data

import com.vaxapp.thingspeakviewer.domain.DomainResponse
import com.vaxapp.thingspeakviewer.domain.OfficeWeatherRepository
import io.reactivex.Single

class DataOfficeWeatherRepository(
    private val dataSource: OfficeWeatherCloudDataSource,
    private val mapper: DomainResponseMapper
) : OfficeWeatherRepository {

    override fun getOfficeWeather(): Single<DomainResponse> {
        return dataSource.fetchFields().map {
            mapper.toDomainResponse(it)
        }
    }
}
