package com.vaxapp.thingspeakviewer.data

data class ApiResponse(
    val channel: Channel,
    val feeds: List<Feed>
)

data class Channel(
    val field1: String,
    val field2: String,
    val description: String,
    val updated_at: String
)

data class Feed(
    val field1: String,
    val field2: String,
    val created_at: String
)
