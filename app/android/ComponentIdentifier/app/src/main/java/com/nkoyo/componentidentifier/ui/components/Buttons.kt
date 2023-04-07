package com.nkoyo.componentidentifier.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.ui.theme.LocalBlack
import com.nkoyo.componentidentifier.ui.theme.LocalGray
import com.nkoyo.componentidentifier.ui.theme.LocalWhite

@Composable
fun ShowRecordButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
){
    Surface(
        modifier = modifier
            .size(36.dp)
            .clip(CircleShape)
            .clickable { onClick() },
        color = LocalGray,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://example.com/image.jpg")
                .crossfade(true)
                .build(),
            contentDescription = stringResource(id = R.string.flip_camera),
            modifier = modifier
                .size(24.dp)
                .clip(CircleShape)
                .padding(8.dp),
            placeholder = painterResource(id = R.drawable.icon_item),
            contentScale = ContentScale.Crop,
        )
    }
}


@Composable
fun CameraFlipButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Surface(
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .clickable { onClick() },
        color = LocalGray,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_flip),
            contentDescription = stringResource(id = R.string.flip_camera),
            modifier = modifier
                .size(24.dp)
                .padding(8.dp),
            tint = LocalWhite,
        )
    }
}


@Composable
fun SnapshotButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .size(68.dp)
            .padding(8.dp)
            .clip(CircleShape)
            .clickable { onClick() }, shape = CircleShape,
        color = Color.White,
        border = BorderStroke(
            width = 1.dp,
            color = LocalBlack.copy(0.6f)
        )
    ) {
        Box(
            modifier = Modifier
                .padding(1.dp)
                .background(color = Color.White, shape = CircleShape)
                .border(
                    BorderStroke(
                        width = 1.dp,
                        color = LocalBlack.copy(0.6f)
                    ),
                    shape = CircleShape,
                )
        )
    }
}



@Composable
@Preview
private fun SnapshotButtonPreview(){
    SnapshotButton()
}


@Composable
@Preview
private fun CameraFlipButtonPreview(){
    CameraFlipButton(
        onClick = {}
    )
}


@Composable
@Preview
private fun ShowRecordButtonPreview(){
    ShowRecordButton(
        onClick = {}
    )
}