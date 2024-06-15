package com.vaxapp.thingspeakviewer.data.setting

class NotificationPreferenceDataSource(val store: PreferenceStore) {

    companion object {
        const val SETTING_KEY = "enable_notification"
    }

    suspend fun saveNotificationPreference(value: Boolean) {
        store.setSetting(SETTING_KEY, value)
    }

    suspend fun getNotificationPreference(): Boolean {
        return store.getSetting(SETTING_KEY)
    }
}

