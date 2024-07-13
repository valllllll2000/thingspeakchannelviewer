package com.vaxapp.thingspeak.viewer.view.settings

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vaxapp.thingspeak.viewer.R
import com.vaxapp.thingspeak.viewer.view.settings.ui.theme.ThingspeakchannelviewerTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme

class SettingsActivity : ComponentActivity() {

    private val viewModel: SettingsViewModel by viewModel<SettingsViewModel>()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            viewModel.onSettingChanged(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThingspeakchannelviewerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SettingsView(
                        modifier = Modifier.padding(innerPadding), viewModel = viewModel,
                    )
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.displayNotification.collect { enabledNotification ->
                    if (enabledNotification) {
                        handleNotificationPermissions()
                    }
                }
            }
        }
    }

    private fun handleNotificationPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}

@Composable
fun SettingsView(modifier: Modifier = Modifier, viewModel: SettingsViewModel) {
    Column(modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Text(
            text = stringResource(R.string.settings_label),
            style = MaterialTheme.typography.headlineLarge
        )
        var checkedState = viewModel.displayNotification.collectAsState().value

        Row(modifier = Modifier
            .semantics {
                stateDescription = if (checkedState) {
                    "Enabled"
                } else {
                    "Disabled"
                }
                role = androidx.compose.ui.semantics.Role.Checkbox
            }
            .padding(top = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {

            Checkbox(checked = checkedState, onCheckedChange = {
                checkedState = it
                viewModel.onSettingChanged(it)
            })
            Text(
                text = stringResource(R.string.notify_channel_data_expired),
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
