package com.nkoyo.componentidentifier.ui.screens.history

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.domain.extensions.asString
import com.nkoyo.componentidentifier.ui.components.NkSearchBar
import com.nkoyo.componentidentifier.ui.components.NkSimpleTopBar
import java.time.LocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailsScreen(
    windowSizeClass: WindowSizeClass,
    fullWidth: Dp = Dp.Unspecified,
    onBackPressed: () -> Unit = {},
    iconColors: IconButtonColors = IconButtonDefaults.iconButtonColors(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.outline,
    ),

    ) {
    Scaffold(
        topBar = {
            if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
                NkSimpleTopBar(
                    title = {
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.records),
                                style = MaterialTheme.typography.titleMedium,
                            )

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
                    actions = {}
                )
            }
        },
        content = {
            Box(Modifier.padding(it)) {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp)) {
                    val detail = HistoryDetailItem(
                        historyItem = HistoryItem(
                            componentName = "Resistor",
                            value = "230Ohms",
                            dateTime = LocalDateTime.now(),
                        ),
                        imageUri = Uri.parse("https://img.freepik.com/free-photo/isolated-happy-smiling-dog-white-background-portrait-4_1562-693.jpg")
                    )

                    image(
                        detail = detail,
                        fullWidth = fullWidth,
                        windowSizeClass = windowSizeClass,
                    )
                    info(
                        detail = detail,
                    )
                }
            }
        }
    )
}

private fun LazyListScope.image(
    detail: HistoryDetailItem,
    fullWidth: Dp,
    windowSizeClass: WindowSizeClass,
) {
    item {
        val imageSize: Pair<Dp, Dp> = when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> Pair(fullWidth, fullWidth)
            else -> Pair(fullWidth.times(0.6f), fullWidth.times(0.6f))
        }

        AsyncImage(
            modifier = Modifier
                .height(imageSize.second)
                .width(imageSize.first),
            model = ImageRequest.Builder(LocalContext.current)
                .data(detail.imageUri)
                .build(),
            contentDescription = stringResource(id = R.string.saved_photo),
        )
        Spacer(Modifier.height(12.dp))
    }
}

private fun LazyListScope.info(
    detail: HistoryDetailItem,
) {
    item {
        Column {
            Text(
                text = detail.historyItem.componentName,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                )
            )
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = detail.historyItem.value,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = detail.historyItem.dateTime.asString(),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
        Spacer(Modifier.height(12.dp))
    }
}


data class HistoryDetailItem(
    val historyItem: HistoryItem,
    val imageUri: Uri,
)