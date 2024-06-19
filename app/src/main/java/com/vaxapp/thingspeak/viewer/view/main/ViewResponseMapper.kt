package com.vaxapp.thingspeak.viewer.view.main

import com.vaxapp.thingspeak.viewer.domain.DomainResponse
import java.text.SimpleDateFormat
import java.util.Date

class ViewResponseMapper(private val localDateFormat: SimpleDateFormat) {

    internal fun toViewResponse(response: DomainResponse): ViewResponse {
        val domainFeed = response.feeds[0]
        return ViewResponse(
            response.channel.description, domainFeed.field1,
            domainFeed.field2, getFormattedDate(domainFeed.createdAt)
        )
    }

    private fun getFormattedDate(date: Date?): String {
        return date?.let { localDateFormat.format(it) } ?: "Unknown"
    }
}
