package com.vaxapp.thingspeakviewer.domain

class SetNotificationPreferenceUseCase(private val repository: NotificationPreferenceRepository) {

    suspend fun saveNotificationPreference(value: Boolean) =
        repository.saveNotificationPreference(value)

}
