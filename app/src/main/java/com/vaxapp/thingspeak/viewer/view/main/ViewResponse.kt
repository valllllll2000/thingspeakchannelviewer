package com.vaxapp.thingspeak.viewer.view.main

data class ViewResponse(
    val description: String,
    val humidity: String,
    val temperature: String,
    val channelUpdateDate: String,
)
