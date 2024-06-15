package com.vaxapp.thingspeakviewer.domain

interface NotificationPreferenceRepository {
    suspend fun saveNotificationPreference(value: Boolean)
    suspend fun getNotificationPreference(): Boolean
}
