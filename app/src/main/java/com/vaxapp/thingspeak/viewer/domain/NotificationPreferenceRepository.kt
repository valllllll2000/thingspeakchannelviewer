package com.vaxapp.thingspeak.viewer.domain

interface NotificationPreferenceRepository {
    suspend fun saveNotificationPreference(value: Boolean)
    suspend fun getNotificationPreference(): Boolean
}
