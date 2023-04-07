package com.nkoyo.componentidentifier.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.nkoyo.componentidentifier.ui.components.CameraFlipButton
import com.nkoyo.componentidentifier.ui.components.ShowRecordButton
import com.nkoyo.componentidentifier.ui.components.SnapshotButton
import com.nkoyo.componentidentifier.ui.theme.LocalBlack



@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainPreviewScreenContent(
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize().padding(bottom = 32.dp),
        contentAlignment = Alignment.BottomCenter,
    ){
        val parentWidth = maxWidth
        BottomActionButtons(parentWidth = parentWidth)
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
        ){
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
private fun BottomActionButtonsPreview(){
    BottomActionButtons(
        parentWidth = 400.dp
    )
}