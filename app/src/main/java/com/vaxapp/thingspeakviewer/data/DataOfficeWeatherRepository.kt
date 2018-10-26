package com.vaxapp.thingspeakviewer.data

import com.vaxapp.thingspeakviewer.domain.DomainChannel
import com.vaxapp.thingspeakviewer.domain.DomainFeed
import com.vaxapp.thingspeakviewer.domain.DomainResponse
import com.vaxapp.thingspeakviewer.domain.OfficeWeatherRepository
import io.reactivex.Single


class DataOfficeWeatherRepository(private val dataSource: OfficeWeatherCloudDataSource) : OfficeWeatherRepository {

    //TODO: add mappers
    override fun getOfficeWeather(): Single<DomainResponse> {
        return dataSource.fetchFields().map { it ->
            DomainResponse(
                    DomainChannel(it.channel.field1, it.channel.field2, it.channel.description, it.channel.updated_at),
                    it.feeds.map { DomainFeed(it.field1, it.field2, it.created_at) })
        }
    }
}
