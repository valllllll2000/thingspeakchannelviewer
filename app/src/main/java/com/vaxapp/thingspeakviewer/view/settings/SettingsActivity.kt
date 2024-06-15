package com.vaxapp.thingspeakviewer.view.settings

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
import com.vaxapp.thingspeakviewer.R
import com.vaxapp.thingspeakviewer.view.settings.ui.theme.ThingspeakchannelviewerTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : ComponentActivity() {
    private val viewModel: SettingsViewModel by viewModel<SettingsViewModel>()
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
    }
}

@Composable
fun SettingsView(modifier: Modifier = Modifier, viewModel: SettingsViewModel) {
    Text(
        text = stringResource(R.string.settings_label),
        modifier = modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    )
    var checkedState = viewModel.displayNotification.collectAsState().value

    Row(
        modifier = Modifier
            .semantics {
                stateDescription = if (checkedState) {
                    "Enabled"
                } else {
                    "Disabled"
                }
                role = androidx.compose.ui.semantics.Role.Checkbox
            }
            .padding(start = 16.dp, top = 72.dp, bottom = 8.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(checked = checkedState, onCheckedChange = {
            checkedState = it
            viewModel.onSettingChanged(it)
        })
        Text(
            text = "Notify channel data expired",
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
