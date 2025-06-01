package com.vaxapp.thingspeak.viewer.data.ip

data class IpResponse(
    val feeds: List<Feed>
)

data class Feed(
    val field1: String,
    val created_at: String
)
