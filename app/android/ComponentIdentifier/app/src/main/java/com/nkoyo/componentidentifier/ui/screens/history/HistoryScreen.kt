package com.nkoyo.componentidentifier.ui.screens.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.ModifierLocalMap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.ui.components.DarkModeConfigSettingsPane
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import com.nkoyo.componentidentifier.ui.components.NkSearchBar
import com.nkoyo.componentidentifier.ui.components.NkSimpleTopBar
import com.nkoyo.componentidentifier.ui.theme.LocalBackgroundTheme
import com.nkoyo.componentidentifier.ui.viewmodel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    mainViewModel: MainViewModel,
    windowSizeClass: WindowSizeClass,
    onBackPressed: () -> Unit,
    onStartSearch: () -> Unit,
    onHistoryDetails: () -> Unit,
    iconColors: IconButtonColors = IconButtonDefaults.iconButtonColors(
        containerColor = Color.Transparent,
        contentColor = LocalBackgroundTheme.current.outline,
    )
) {
    val configuration = LocalConfiguration.current
    val menuState by mainViewModel.menuState.collectAsStateWithLifecycle()
    val darkModeDialogState by mainViewModel.darkModeDialogState.collectAsStateWithLifecycle()
    val darkThemeConfigSettings by mainViewModel.darkThemeConfigSettings.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = LocalBackgroundTheme.current.surface,
        modifier = Modifier,
        topBar = {
            NkSimpleTopBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LocalBackgroundTheme.current.surface,
                    scrolledContainerColor = LocalBackgroundTheme.current.surface,
                    navigationIconContentColor = LocalBackgroundTheme.current.outline,
                    titleContentColor = LocalBackgroundTheme.current.outline,
                    actionIconContentColor = LocalBackgroundTheme.current.outline,
                ),
                title = {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.records),
                            style = MaterialTheme.typography.titleMedium,
                        )

                        if (windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact) {
                            Spacer(Modifier.width(64.dp))
                            //search bar
                            NkSearchBar(
                                value = "",
                                onValueChange = {},
                                fullWidth = configuration.screenWidthDp.dp
                            )
                        }
                    }
                },
                navIcon = {
                    Box(Modifier.padding(8.dp)) {
                        IconButton(
                            onClick = onBackPressed,
                            enabled = true,
                            colors = iconColors,
                            content = {
                                Icon(
                                    painterResource(id = R.drawable.icon_back),
                                    contentDescription = stringResource(id = R.string.close),
                                )
                            }
                        )
                    }
                },
                actions = {
                    if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
                        Box(Modifier.padding(4.dp)) {
                            IconButton(
                                onClick = onStartSearch,
                                enabled = true,
                                colors = iconColors,
                                content = {
                                    Icon(
                                        painterResource(id = R.drawable.icon_search),
                                        contentDescription = stringResource(id = R.string.close),
                                    )
                                }
                            )
                        }
                    }

                    Box(Modifier.padding(4.dp)) {
                        IconButton(
                            onClick = { mainViewModel.updateMenuState(true) },
                            enabled = true,
                            colors = iconColors,
                            content = {
                                Icon(
                                    painterResource(id = R.drawable.icon_more_vert),
                                    contentDescription = stringResource(id = R.string.options),
                                )
                            }
                        )
                    }
                }
            )
        },
        contentColor = LocalBackgroundTheme.current.outline,
        content = {
            HistoryScreenContent(
                modifier = Modifier.padding(it),
                mainViewModel = mainViewModel,
                windowSizeClass = windowSizeClass,
                onClick = {
                    //todo -> mark selected item
                    if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact)
                        onHistoryDetails()
                }
            )
        }
    )

    if (menuState) {
        val showDarkModeDialog = { mainViewModel.updateDarkModeDialogState(true) }
        val onDismiss = { mainViewModel.updateMenuState(false) }

        Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxSize()) {
            Popup(
                alignment = Alignment.TopCenter,
                offset = IntOffset(200, 30),
                onDismissRequest = { onDismiss() }
            ) {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = LocalBackgroundTheme.current.surfaceVariant,
                        contentColor = LocalBackgroundTheme.current.onSurfaceVariant,
                    )
                ) {
                    Box(
                        Modifier.clickable(
                            enabled = true,
                            onClick = {
                                showDarkModeDialog()
                                onDismiss()
                            },
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.dark_theme),
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = LocalBackgroundTheme.current.onSurfaceVariant
                            ),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }

    if (darkModeDialogState) {
        val dismissDialog = { mainViewModel.updateDarkModeDialogState(false) }

        Box(Modifier.fillMaxSize()) {
            AlertDialog(
                containerColor = LocalBackgroundTheme.current.surfaceVariant,
                textContentColor = LocalBackgroundTheme.current.onSurfaceVariant,
                titleContentColor = LocalBackgroundTheme.current.onSurfaceVariant,
                onDismissRequest = { dismissDialog() },
                confirmButton = {
                    TextButton(
                        onClick = { dismissDialog() },
                        content = {
                            Text(
                                text = stringResource(id = R.string.finish),
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = LocalBackgroundTheme.current.onSurfaceVariant
                        )
                    )
                },
                dismissButton = {
                    TextButton(
                        onClick = { dismissDialog() },
                        content = {
                            Text(
                                text = stringResource(id = R.string.cancel),
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = LocalBackgroundTheme.current.onSurfaceVariant
                        )
                    )
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.dark_mode_settings),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                shape = RoundedCornerShape(16.dp),
                properties = DialogProperties(usePlatformDefaultWidth = false),
                modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 16.dp),
                tonalElevation = 2.dp,
                text = {
                    Divider()
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        DarkModeConfigSettingsPane(
                            onDarkThemeConfigChanged = mainViewModel::updateDarkThemeConfigState,
                            settings = darkThemeConfigSettings
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun HistoryScreenContent(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    windowSizeClass: WindowSizeClass,
    onClick: (HistoryItem) -> Unit,
) {
    Row(
        Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        val configuration = LocalConfiguration.current

        HistoryListScreen(onClick = onClick, mainViewModel = mainViewModel)
        Row(
            Modifier
                .fillMaxSize()
                .padding(top = 32.dp)) {

            HistoryListScreen(onClick = onClick, mainViewModel = mainViewModel)

            if (windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact) {
                Box(Modifier.width(configuration.screenWidthDp.dp.times(0.67f))) {
                    HistoryDetailsScreen(
                        windowSizeClass = windowSizeClass,
                        fullWidth = configuration.screenWidthDp.dp,
                        mainViewModel = mainViewModel
                    )
                }
            }
        }
    }
}
