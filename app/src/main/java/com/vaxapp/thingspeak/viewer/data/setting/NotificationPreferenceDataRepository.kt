package com.vaxapp.thingspeak.viewer.data.setting

import com.vaxapp.thingspeak.viewer.domain.NotificationPreferenceRepository

class NotificationPreferenceDataRepository(private val dataSource: NotificationPreferenceDataSource) :
    NotificationPreferenceRepository {
    override suspend fun saveNotificationPreference(value: Boolean) =
        dataSource.saveNotificationPreference(value)

    override suspend fun getNotificationPreference(): Boolean = dataSource.getNotificationPreference()
}
