package com.nkoyo.componentidentifier.ui.screens.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.ui.components.CircleIconButton
import com.nkoyo.componentidentifier.ui.components.SecondaryButton
import com.nkoyo.componentidentifier.ui.theme.LocalBackgroundTheme
import com.nkoyo.componentidentifier.ui.viewmodel.EMPTY_FILE
import java.time.LocalDateTime


@Composable
fun DynamicBottomSheet(
    context: Context = LocalContext.current,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    maxWidth: Dp,
    maxHeight: Dp,
    onPreviewWebInfo: () -> Unit,
    openUrl: (String) -> Unit,
    windowSizeClass: WindowSizeClass,
    rotationAngle: Float,
    minimized: Boolean,
    onScale: () -> Unit,
    info: ComponentInfo,
    contentDesc: String,
) {
    DynamicBottomSheet(
        modifier = modifier,
        bottomSheetMinimized = minimized,
        maxWidth = maxWidth,
        maxHeight = maxHeight,
        onPreviewWebInfo = onPreviewWebInfo,
        openUrl = openUrl,
        rotationAngle = rotationAngle,
        info = info,
        windowSizeClass = windowSizeClass,
        onScale = onScale,
        contentDesc = contentDesc,
    )
}


@Composable
private fun DynamicBottomSheet(
    modifier: Modifier = Modifier,
    bottomSheetMinimized: Boolean,
    maxWidth: Dp,
    maxHeight: Dp,
    onPreviewWebInfo: () -> Unit,
    openUrl: (String) -> Unit,
    rotationAngle: Float,
    onScale: () -> Unit,
    info: ComponentInfo,
    contentDesc: String,
    windowSizeClass: WindowSizeClass,
) {
    val calculatedHeight = if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
        maxHeight.times(0.4f)
    } else maxHeight
    val height by animateDpAsState(targetValue = if (bottomSheetMinimized) 32.dp else calculatedHeight)
    val width by animateDpAsState(targetValue = if (bottomSheetMinimized) maxWidth.times(0.2f) else maxWidth)

    Card(
        modifier = modifier
            .height(height)
            .width(width)
            .semantics { contentDescription = contentDesc },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            focusedElevation = 2.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = LocalBackgroundTheme.current.surfaceVariant,
            contentColor = LocalBackgroundTheme.current.onSurfaceVariant
        ),
    ) {
        if (!bottomSheetMinimized) {
            Spacer(modifier = modifier.height(8.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = modifier
                        .width(maxWidth.times(0.22f))
                        .height(4.dp)
                        .background(
                            color = LocalBackgroundTheme.current.onSurfaceVariant.copy(0.6f),
                            shape = CircleShape
                        ),
                )
            }


            Box(Modifier.padding(16.dp)) {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp), contentAlignment = Alignment.CenterEnd
                ) {
                    CircleIconButton(
                        icon = R.drawable.icon_minimize,
                        contentDesc = stringResource(id = R.string.minimize_button),
                        onClick = onScale,
                        surfaceColor = Color.Transparent,
                        borderColor = Color.Transparent,
                        rotationAngle = rotationAngle,
                        tint = LocalBackgroundTheme.current.onSurfaceVariant
                    )
                }

                Spacer(modifier = modifier.height(12.dp))

                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    Text(
                        text = info.componentName,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = LocalBackgroundTheme.current.onSurfaceVariant,
                        )
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = info.description,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = LocalBackgroundTheme.current.onSurfaceVariant
                        ),
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(Modifier.height(24.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        if(info.componentName.isNotBlank()) {
                            SecondaryButton(
                                label = stringResource(id = R.string.read_more),
                                onClick = {
                                    openUrl(info.url)
                                    onPreviewWebInfo()
                                },
                                contentColor = LocalBackgroundTheme.current.onSurfaceVariant,
                                contentDesc = stringResource(id = R.string.read_more_button)
                            )
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = modifier
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                CircleIconButton(
                    icon = R.drawable.icon_maximize,
                    contentDesc = stringResource(id = R.string.maximize_button),
                    onClick = onScale,
                    surfaceColor = Color.Transparent,
                    borderColor = Color.Transparent,
                    rotationAngle = rotationAngle,
                    tint = LocalBackgroundTheme.current.onSurfaceVariant,
                )
            }
        }

    }
}


data class ComponentInfo(
    val componentName: String,
    val description: String,
    val url: String,
    val dateTime: LocalDateTime,
) {
    companion object {
        val DEFAULT = ComponentInfo(
            componentName = "",
            description = "",
            url = EMPTY_FILE,
            dateTime = LocalDateTime.MIN
        )
    }
}