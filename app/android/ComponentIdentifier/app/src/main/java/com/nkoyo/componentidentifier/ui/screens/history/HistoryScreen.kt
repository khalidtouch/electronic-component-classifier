package com.nkoyo.componentidentifier.ui.screens.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.data.fake.fakeHistoryItem
import com.nkoyo.componentidentifier.ui.components.CircleIconButton
import com.nkoyo.componentidentifier.ui.components.NkSearchBar
import com.nkoyo.componentidentifier.ui.components.NkSimpleTopBar
import com.nkoyo.componentidentifier.ui.viewmodel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    mainViewModel: MainViewModel,
    windowSizeClass: WindowSizeClass,
    onBackPressed: () -> Unit,
    onStartSearch: () -> Unit,
    onOptionsPressed: () -> Unit = {},
    onHistoryDetails: () -> Unit,
    iconColors: IconButtonColors = IconButtonDefaults.iconButtonColors(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.outline,
    )
) {
    val configuration = LocalConfiguration.current

    Scaffold(
        modifier = Modifier,
        topBar = {
            NkSimpleTopBar(
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
                            onClick = onOptionsPressed,
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
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.outline,
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
}


@Composable
private fun HistoryScreenContent(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    windowSizeClass: WindowSizeClass,
    onClick: (HistoryItem) -> Unit,
) {
    Row(Modifier.fillMaxSize().padding(top = 32.dp)) {
        val configuration = LocalConfiguration.current

        HistoryListScreen( onClick = onClick, mainViewModel = mainViewModel)

        if (windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact) {
            Box(Modifier.width(configuration.screenWidthDp.dp.times(0.67f))) {
                HistoryDetailsScreen(
                    windowSizeClass = windowSizeClass,
                    fullWidth = configuration.screenWidthDp.dp,
                )
            }
        }
    }
}

