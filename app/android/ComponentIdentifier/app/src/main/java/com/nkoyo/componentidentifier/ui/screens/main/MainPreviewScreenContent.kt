package com.nkoyo.componentidentifier.ui.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.domain.usecases.ImageCaptureFlashMode
import com.nkoyo.componentidentifier.ui.components.CameraFlipButton
import com.nkoyo.componentidentifier.ui.components.CircleIconButton
import com.nkoyo.componentidentifier.ui.components.ShowRecordButton
import com.nkoyo.componentidentifier.ui.components.SnapshotButton


@Composable
fun MainPreviewScreenContent(
    modifier: Modifier = Modifier,
    rotationAngle: Float,
    gettingStartedState: Boolean,
    isBottomSheetMinimized: Boolean,
    windowSizeClass: WindowSizeClass,
    onClose: () -> Unit = {},
    onToggleFlashLight: () -> Unit = {},
    onTakeSnapshot: () -> Unit = {},
    onToggleCamera: () -> Unit = {},
    onViewRecords: () -> Unit = {},
    flashLightState: ImageCaptureFlashMode,
    bottomSheet: @Composable () -> Unit,
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 32.dp, top = 16.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        val parentWidth = maxWidth
        Row(Modifier.matchParentSize()) {
            if(!gettingStartedState) {
                Box(Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter
                    ) {
                        TopActionButtons(
                            onClose = onClose,
                            onToggleFlashLight = onToggleFlashLight,
                            flashLightState = flashLightState,
                            rotationAngle = rotationAngle,
                        )
                    }

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                        BottomActionButtons(
                            parentWidth = parentWidth,
                            onTakeSnapshot = onTakeSnapshot,
                            onViewRecords = onViewRecords,
                            onToggleCamera = onToggleCamera,
                            rotationAngle = rotationAngle,
                        )
                    }
                }
            }
            if (windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = !gettingStartedState,
                    enter = slideInHorizontally(
                        animationSpec = tween(
                            durationMillis = 200,
                            delayMillis = 2,
                        ),
                        initialOffsetX = { -it }
                    ),
                    exit = slideOutHorizontally(
                        animationSpec = tween(durationMillis = 2),
                        targetOffsetX = { -it })
                ) {
                    Box(
                        Modifier
                            .width(parentWidth.times(0.3f))
                            .fillMaxHeight()
                    ) {
                        bottomSheet()
                    }
                }
            }
        }
    }
}

@Composable
fun TopActionButtons(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    rotationAngle: Float,
    onToggleFlashLight: () -> Unit,
    flashLightState: ImageCaptureFlashMode,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircleIconButton(
            modifier = modifier,
            rotationAngle = rotationAngle,
            icon = R.drawable.icon_close,
            contentDescription = stringResource(id = R.string.close),
            onClick = onClose,
            surfaceColor = MaterialTheme.colorScheme.primary,
            borderColor = Color.Transparent,
            tint = Color.Black
        )

        if (flashLightState !is ImageCaptureFlashMode.Off) {
            CircleIconButton(
                modifier = modifier,
                rotationAngle = rotationAngle,
                icon = R.drawable.icon_lightning,
                contentDescription = stringResource(id = R.string.toggle_flashlight),
                onClick = onToggleFlashLight,
                surfaceColor = Color.Transparent,
                borderColor = MaterialTheme.colorScheme.primary,
                tint = MaterialTheme.colorScheme.primary,
                selected = flashLightState is ImageCaptureFlashMode.On,
            )
        } else {
            Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                CircleIconButton(
                    modifier = modifier,
                    rotationAngle = rotationAngle,
                    icon = R.drawable.icon_lightning,
                    contentDescription = stringResource(id = R.string.toggle_flashlight),
                    onClick = onToggleFlashLight,
                    surfaceColor = Color.Transparent,
                    borderColor = MaterialTheme.colorScheme.primary,
                    tint = MaterialTheme.colorScheme.primary,
                )

                Icon(
                    painter = painterResource(id = R.drawable.icon_backslash),
                    contentDescription = stringResource(id = R.string.backslash),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = modifier
                        .size(24.dp)
                        .rotate(rotationAngle)
                )
            }
        }
    }
}


@Composable
fun BottomActionButtons(
    modifier: Modifier = Modifier,
    parentWidth: Dp,
    onTakeSnapshot: () -> Unit = {},
    onToggleCamera: () -> Unit = {},
    onViewRecords: () -> Unit = {},
    rotationAngle: Float,
    color: Color = Color.Black,
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = color.copy(0.8f)
    ) {
        Row(
            modifier = modifier
                .width(parentWidth.times(.55f))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShowRecordButton(
                onClick = onViewRecords,
                modifier = modifier,
                rotationAngle = rotationAngle,
            )

            SnapshotButton(
                onClick = onTakeSnapshot,
                rotationAngle = rotationAngle,
                modifier = modifier,
            )

            CameraFlipButton(
                onClick = onToggleCamera,
                rotationAngle = rotationAngle,
                modifier = modifier,
            )
        }
    }
}


@Composable
@Preview
private fun BottomActionButtonsPreview() {
    BottomActionButtons(
        parentWidth = 400.dp,
        rotationAngle = 23f,
    )
}