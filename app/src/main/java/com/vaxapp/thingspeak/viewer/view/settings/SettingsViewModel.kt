package com.vaxapp.thingspeak.viewer.view.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaxapp.thingspeak.viewer.domain.GetNotificationPreferenceUseCase
import com.vaxapp.thingspeak.viewer.domain.SetNotificationPreferenceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val notificationPreferenceUseCase: GetNotificationPreferenceUseCase,
    private val setNotificationPreferenceUseCase: SetNotificationPreferenceUseCase
) :
    ViewModel() {

    private val _displayNotification = MutableStateFlow(false)

    val displayNotification: StateFlow<Boolean> = _displayNotification.asStateFlow()

    init {
        viewModelScope.launch {
            _displayNotification.value = notificationPreferenceUseCase.getNotificationPreference()
        }
    }

    fun onSettingChanged(value: Boolean) {
        viewModelScope.launch {
            setNotificationPreferenceUseCase.saveNotificationPreference(value)
        }
        _displayNotification.value = value
    }
}
