package com.vaxapp.thingspeak.viewer.domain

class SetNotificationPreferenceUseCase(private val repository: NotificationPreferenceRepository) {

    suspend fun saveNotificationPreference(value: Boolean) =
        repository.saveNotificationPreference(value)

}
