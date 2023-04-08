package com.nkoyo.componentidentifier.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.ui.components.CameraFlipButton
import com.nkoyo.componentidentifier.ui.components.CircleIconButton
import com.nkoyo.componentidentifier.ui.components.ShowRecordButton
import com.nkoyo.componentidentifier.ui.components.SnapshotButton
import com.nkoyo.componentidentifier.ui.theme.LocalBlack
import com.nkoyo.componentidentifier.ui.theme.LocalWhite


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainPreviewScreenContent(
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    onToggleFlashLight: () -> Unit = {},
    flashLightState: MutableState<Boolean> = remember { mutableStateOf(false)},
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 32.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        val parentWidth = maxWidth
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter
        ) {
            TopActionButtons(
                onClose = onClose,
                onToggleFlashLight = onToggleFlashLight,
                flashLightState = flashLightState,
            )
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            BottomActionButtons(parentWidth = parentWidth)
        }
    }
}

@Composable
fun TopActionButtons(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    onToggleFlashLight: () -> Unit,
    flashLightState: MutableState<Boolean> = remember { mutableStateOf(false)},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 16.dp,
                horizontal = 16.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircleIconButton(
            icon = R.drawable.icon_close,
            contentDescription = stringResource(id = R.string.close),
            onClick = onClose,
            surfaceColor = LocalWhite,
            borderColor = LocalWhite,
            tint = LocalBlack
        )

        CircleIconButton(
            icon = R.drawable.icon_lightning,
            contentDescription = stringResource(id = R.string.toggle_flashlight),
            onClick = onToggleFlashLight,
            surfaceColor = Color.Transparent,
            borderColor = LocalWhite,
            tint = LocalWhite,
            selected = flashLightState.value,
        )
    }
}


@Composable
fun BottomActionButtons(
    modifier: Modifier = Modifier,
    parentWidth: Dp,
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = LocalBlack.copy(0.8f)
    ) {
        Row(
            modifier = modifier
                .width(parentWidth.times(.55f))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShowRecordButton(
                onClick = {}
            )

            SnapshotButton(
                onClick = {}
            )

            CameraFlipButton(
                onClick = {}
            )
        }
    }
}


@Composable
@Preview
private fun BottomActionButtonsPreview() {
    BottomActionButtons(
        parentWidth = 400.dp
    )
}