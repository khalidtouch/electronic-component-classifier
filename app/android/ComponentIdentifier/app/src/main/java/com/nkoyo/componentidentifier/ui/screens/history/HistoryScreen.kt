package com.nkoyo.componentidentifier.ui.screens.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.nkoyo.componentidentifier.data.fake.fakeHistoryItem
import com.nkoyo.componentidentifier.ui.viewmodel.MainViewModel


@Composable
fun HistoryScreen(
    mainViewModel: MainViewModel,
    windowSizeClass: WindowSizeClass,
) {
    Scaffold(
        modifier = Modifier,
        topBar = {

        },
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.outline,
        content = {
            HistoryScreenContent(
                modifier = Modifier.padding(it),
                mainViewModel = mainViewModel,
                windowSizeClass = windowSizeClass,
            )
        }
    )
}


@Composable
private fun HistoryScreenContent(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    windowSizeClass: WindowSizeClass,
) {
    Row(Modifier.fillMaxSize()) {
        val configuration = LocalConfiguration.current

        HistoryListScreen(historyItems = fakeHistoryItem, onClick = {})

        if (windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact) {
            Box(Modifier.width(configuration.screenWidthDp.dp.times(0.67f))) {
                HistoryDetailsScreen()
            }
        }
    }
}

