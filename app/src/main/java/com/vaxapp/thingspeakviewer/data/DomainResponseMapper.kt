package com.vaxapp.thingspeakviewer.data

import com.vaxapp.thingspeakviewer.domain.DomainChannel
import com.vaxapp.thingspeakviewer.domain.DomainFeed
import com.vaxapp.thingspeakviewer.domain.DomainResponse
import java.text.SimpleDateFormat

class DomainResponseMapper(private val serverDateFormat: SimpleDateFormat) {

    fun toDomainResponse(it: ApiResponse) = DomainResponse(
        DomainChannel(
            it.channel.field1,
            it.channel.field2,
            it.channel.description,
            it.channel.updated_at
        ),
        it.feeds.map { DomainFeed(it.field1, it.field2, serverDateFormat.parse(it.created_at)) })
}