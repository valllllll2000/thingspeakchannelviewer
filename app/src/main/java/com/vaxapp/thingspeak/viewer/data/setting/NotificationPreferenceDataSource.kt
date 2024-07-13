package com.vaxapp.thingspeak.viewer.data.setting

class NotificationPreferenceDataSource(private val store: PreferenceStore) {

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

