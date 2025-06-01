package com.vaxapp.thingspeak.viewer.view.ip

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun IpComposable(viewModel: IpViewModel) {
    val value = viewModel.state.collectAsState().value
    Text(value, textAlign = TextAlign.Center, modifier = Modifier.clickable {})
}