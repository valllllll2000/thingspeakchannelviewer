package com.vaxapp.thingspeak.viewer.view.ip

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class IpViewModel : ViewModel() {

    private var _state = MutableStateFlow("")
    val state: StateFlow<String> = _state.asStateFlow()

}
