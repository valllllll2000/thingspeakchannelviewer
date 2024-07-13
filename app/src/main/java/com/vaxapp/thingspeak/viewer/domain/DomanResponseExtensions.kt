package com.vaxapp.thingspeak.viewer.domain

import java.util.Date

//20 minutes
private const val MAX_UPDATE_DELAY = 1200000L
private const val MAX_UPDATE_DELAY_TEST = 5000L

fun DomainResponse.channelDataTooOld(): Boolean {
    val channelUpdateTime = feeds.first().createdAt?.time ?: 0L
    return Date().time - channelUpdateTime > MAX_UPDATE_DELAY
}
