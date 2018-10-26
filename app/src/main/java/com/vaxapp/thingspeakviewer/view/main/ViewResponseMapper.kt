package com.vaxapp.thingspeakviewer.view.main

import com.vaxapp.thingspeakviewer.domain.DomainFeed
import com.vaxapp.thingspeakviewer.domain.DomainResponse
import java.text.SimpleDateFormat


class ViewResponseMapper(private val serverDateFormat: SimpleDateFormat, private val localDateFormat: SimpleDateFormat) {

    internal fun toViewResponse(response: DomainResponse): ViewResponse {
        val domainFeed = response.feeds[0]
        return ViewResponse(response.channel.description, domainFeed.field1,
                domainFeed.field2, getFormattedDate(domainFeed))
    }

    private fun getFormattedDate(domainFeed: DomainFeed): String {
        val date = serverDateFormat.parse(domainFeed.created_at)
        return localDateFormat.format(date)
    }
}
