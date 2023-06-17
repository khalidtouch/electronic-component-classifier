package com.nkoyo.componentidentifier.ui.screens.main

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.ui.components.CircleIconButton

@Composable
fun PhotoCaptureScreen(
    imageUri: Uri,
    onRemove: () -> Unit,
    rotationAngle: Float,
) {
    Surface(modifier = Modifier) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUri)
                    .build(),
                contentDescription = stringResource(id = R.string.saved_photo),
            )

            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter
            ) {
                RemoveSavedPhotoButton(
                    onRemove = onRemove,
                    rotationAngle = rotationAngle,
                )
            }
        }
    }
}


@Composable
fun RemoveSavedPhotoButton(
    modifier: Modifier = Modifier,
    rotationAngle: Float,
    onRemove: () -> Unit,
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
            modifier = modifier,
            rotationAngle = rotationAngle,
            icon = R.drawable.icon_close,
            contentDesc = stringResource(id = R.string.close_capture_button),
            onClick = onRemove,
            surfaceColor = Color.Transparent,
            borderColor = MaterialTheme.colorScheme.outline,
            tint = MaterialTheme.colorScheme.outline
        )
    }
}