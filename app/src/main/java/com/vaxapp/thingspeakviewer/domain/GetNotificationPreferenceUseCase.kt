package com.vaxapp.thingspeakviewer.domain

class GetNotificationPreferenceUseCase(private val repository: NotificationPreferenceRepository) {

    suspend fun getNotificationPreference(): Boolean = repository.getNotificationPreference()
}
