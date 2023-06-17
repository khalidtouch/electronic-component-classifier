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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.ui.theme.LocalBackgroundTheme

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    label: String,
    style: TextStyle = MaterialTheme.typography.labelLarge,
    onClick: () -> Unit = {},
    contentDesc: String,
    contentColor: Color = LocalBackgroundTheme.current.outline,
    containerColor: Color = LocalBackgroundTheme.current.outline,
) {
    OutlinedButton(
        modifier = modifier
            .clip(CircleShape)
            .semantics { contentDescription = contentDesc },
        onClick = onClick,
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = containerColor,
        )
    ) {
        Text(
            text = label,
            style = style,
            color = contentColor,
            modifier = modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        )
    }
}

@Composable
fun TertiaryButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit = {},
    style: TextStyle = MaterialTheme.typography.labelLarge,
    contentColor: Color = MaterialTheme.colorScheme.outline,
    contentDesc: String,
) {
    OutlinedButton(
        modifier = modifier
            .clip(CircleShape)
            .semantics { contentDescription = contentDesc },
        onClick = onClick,
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = Color.Transparent,
        )
    ) {
        Text(
            text = label,
            style = style,
            color = contentColor,
            modifier = modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        )
    }
}


@Composable
fun ShowRecordButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    rotationAngle: Float,
    color: Color = MaterialTheme.colorScheme.primary,
    contentDesc: String,
) {
    Surface(
        modifier = modifier
            .size(36.dp)
            .clip(CircleShape)
            .clickable { onClick() }
            .rotate(rotationAngle)
            .semantics { contentDescription = contentDesc },
        color = color,
    ) {
        Icon(
            painterResource(id = R.drawable.icon_item),
            contentDescription = null,
            tint = Color.Black.copy(0.7f),
            modifier = Modifier
                .size(24.dp)
                .padding(8.dp)
        )
    }
}

@Composable
fun CircleIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    icon: Int,
    iconSize: Dp = 24.dp,
    surfaceColor: Color = MaterialTheme.colorScheme.primary,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    tint: Color = MaterialTheme.colorScheme.outline,
    padding: Dp = 8.dp,
    rotationAngle: Float,
    selected: Boolean = true,
    contentDesc: String
) {
    Surface(
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .clickable { onClick() }
            .rotate(rotationAngle)
            .semantics { contentDescription = contentDesc },
        color = surfaceColor,
        shape = CircleShape,
        border = BorderStroke(
            1.5.dp, color = if (selected) borderColor else borderColor.copy(0.5f),
        )
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = modifier
                .size(iconSize)
                .clip(CircleShape)
                .padding(padding),
            tint = if (selected) tint else tint.copy(0.5f),
        )
    }
}


@Composable
fun CameraFlipButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    rotationAngle: Float,
    color: Color = MaterialTheme.colorScheme.primary,
    tint: Color = Color.Black,
    contentDesc: String,
) {
    Surface(
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .clickable { onClick() }
            .rotate(rotationAngle)
            .semantics { contentDescription = contentDesc },
        shape = CircleShape,
        color = color,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_flip),
            contentDescription = stringResource(id = R.string.flip_camera),
            modifier = modifier
                .size(24.dp)
                .padding(8.dp),
            tint = tint,
        )
    }
}


@Composable
fun SnapshotButton(
    modifier: Modifier = Modifier,
    rotationAngle: Float,
    onClick: () -> Unit = {},
    color: Color = MaterialTheme.colorScheme.primary,
    tint: Color = MaterialTheme.colorScheme.outline,
    contentDesc: String,
) {
    Surface(
        modifier = Modifier
            .size(68.dp)
            .padding(8.dp)
            .clip(CircleShape)
            .clickable { onClick() }
            .rotate(rotationAngle)
            .semantics { contentDescription = contentDesc }, shape = CircleShape,
        color = Color.White,
        border = BorderStroke(
            width = 1.dp,
            color = color.copy(0.6f)
        )
    ) {
        Box(
            modifier = Modifier
                .padding(1.dp)
                .background(color = Color.White, shape = CircleShape)
                .border(
                    BorderStroke(
                        width = 1.dp,
                        color = tint.copy(0.6f)
                    ),
                    shape = CircleShape,
                )
        )
    }
}


@Composable
@Preview
private fun SnapshotButtonPreview() {
    SnapshotButton(
        rotationAngle = 23f,
        contentDesc = ""
    )
}


@Composable
@Preview
private fun CameraFlipButtonPreview() {
    CameraFlipButton(
        onClick = {},
        rotationAngle = 23f,
        contentDesc = ""
    )
}


@Composable
@Preview
private fun ShowRecordButtonPreview() {
    ShowRecordButton(
        onClick = {},
        rotationAngle = 23f,
        contentDesc = ""
    )
}

@Composable
@Preview
private fun SecondaryButtonPreview() {
    SecondaryButton(
        label = "Click me!",
        onClick = {},
        contentDesc = ""
    )
}