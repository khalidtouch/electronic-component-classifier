package com.nkoyo.componentidentifier.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.datastore.DarkThemeConfig
import com.nkoyo.componentidentifier.ui.theme.LocalBackgroundTheme


@Composable
fun DarkModeConfigSettingsPane(
    settings: DarkThemeConfigSettings,
    onDarkThemeConfigChanged: (DarkThemeConfig) -> Unit,
) {
    Column(Modifier.selectableGroup()) {
        DarkModeConfigRowChoose(
            onClick = { onDarkThemeConfigChanged(DarkThemeConfig.FOLLOW_SYSTEM) },
            selected = settings.darkThemeConfig == DarkThemeConfig.FOLLOW_SYSTEM,
            text = stringResource(id = R.string.dark_mode_config_follow_system)
        )
        DarkModeConfigRowChoose(
            onClick = { onDarkThemeConfigChanged(DarkThemeConfig.DARK) },
            selected = settings.darkThemeConfig == DarkThemeConfig.DARK,
            text = stringResource(id = R.string.dark_mode_config_dark)
        )
        DarkModeConfigRowChoose(
            onClick = { onDarkThemeConfigChanged(DarkThemeConfig.LIGHT) },
            selected = settings.darkThemeConfig == DarkThemeConfig.LIGHT,
            text = stringResource(id = R.string.dark_mode_config_light)
        )
    }
}

@Composable
fun DarkModeConfigRowChoose(
    onClick: () -> Unit,
    selected: Boolean,
    text: String,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                role = Role.RadioButton,
                onClick = onClick,
                selected = selected,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            modifier = Modifier.padding(16.dp),
            colors = RadioButtonDefaults.colors(
                selectedColor = LocalBackgroundTheme.current.outline,
                unselectedColor = LocalBackgroundTheme.current.outline.copy(0.5f)
            )
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}


data class DarkThemeConfigSettings(
    val darkThemeConfig: DarkThemeConfig,
)
