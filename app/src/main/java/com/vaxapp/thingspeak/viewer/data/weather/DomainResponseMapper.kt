package com.vaxapp.thingspeak.viewer.data.weather

import com.vaxapp.thingspeak.viewer.domain.DomainChannel
import com.vaxapp.thingspeak.viewer.domain.DomainFeed
import com.vaxapp.thingspeak.viewer.domain.DomainResponse
import java.text.SimpleDateFormat

class DomainResponseMapper(private val serverDateFormat: SimpleDateFormat) {

    fun toDomainResponse(it: WeatherResponse) = DomainResponse(
        DomainChannel(
            it.channel.field1,
            it.channel.field2,
            it.channel.description,
            it.channel.updated_at
        ),
        it.feeds.map { DomainFeed(it.field1, it.field2, serverDateFormat.parse(it.created_at)) })
}