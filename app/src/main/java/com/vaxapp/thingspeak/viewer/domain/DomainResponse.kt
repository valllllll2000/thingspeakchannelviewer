package com.vaxapp.thingspeak.viewer.domain

import java.util.Date

data class DomainResponse(
    val channel: DomainChannel,
    val feeds: List<DomainFeed>
)

data class DomainChannel(
    val field1: String,
    val field2: String,
    val description: String,
    val updatedAt: String
)

data class DomainFeed(
    val field1: String,
    val field2: String,
    val createdAt: Date?
)
