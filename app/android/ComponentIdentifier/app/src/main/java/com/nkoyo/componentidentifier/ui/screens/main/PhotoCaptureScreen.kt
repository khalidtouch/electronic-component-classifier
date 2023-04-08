package com.nkoyo.componentidentifier.ui.screens.main

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.ui.components.CircleIconButton
import com.nkoyo.componentidentifier.ui.theme.LocalBlack
import com.nkoyo.componentidentifier.ui.theme.LocalWhite


@Composable
fun PhotoCaptureScreen (
    modifier: Modifier = Modifier,
    onRemove: () -> Unit,
    imageUri: MutableState<Uri> = remember { mutableStateOf(Uri.parse("file://dev/null"))},
){
    Surface(modifier = Modifier){
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUri.value)
                    .build(),
                contentDescription = stringResource(id = R.string.saved_photo),
            )

            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter
            ){
                RemoveSavedPhotoButton(
                    onRemove = onRemove
                )
            }
        }
    }
}


@Composable
fun RemoveSavedPhotoButton(
    modifier: Modifier = Modifier,
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
            icon = R.drawable.icon_close,
            contentDescription = stringResource(id = R.string.close),
            onClick = onRemove,
            surfaceColor = LocalWhite,
            borderColor = LocalWhite,
            tint = LocalBlack
        )
    }
}