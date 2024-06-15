package com.vaxapp.thingspeakviewer.data.setting

import com.vaxapp.thingspeakviewer.domain.NotificationPreferenceRepository

class NotificationPreferenceDataRepository(private val dataSource: NotificationPreferenceDataSource) : NotificationPreferenceRepository {
    override suspend fun saveNotificationPreference(value: Boolean) =
        dataSource.saveNotificationPreference(value)

    override suspend fun getNotificationPreference(): Boolean = dataSource.getNotificationPreference()
}
