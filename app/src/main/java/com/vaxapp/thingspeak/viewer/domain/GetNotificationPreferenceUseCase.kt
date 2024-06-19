package com.vaxapp.thingspeak.viewer.domain

class GetNotificationPreferenceUseCase(private val repository: NotificationPreferenceRepository) {

    suspend fun getNotificationPreference(): Boolean = repository.getNotificationPreference()
}
