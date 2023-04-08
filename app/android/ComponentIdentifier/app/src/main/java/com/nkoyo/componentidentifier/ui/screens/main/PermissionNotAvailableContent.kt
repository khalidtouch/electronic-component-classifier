package com.nkoyo.componentidentifier.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.ui.components.SecondaryButton
import com.nkoyo.componentidentifier.ui.components.TertiaryButton
import com.nkoyo.componentidentifier.ui.theme.LocalBlack


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionNotAvailableContent(
    modifier: Modifier = Modifier,
    cameraPermissionState: PermissionState,
    onAbort: () -> Unit = {},
) {
    val message = buildAnnotatedString {
        append(
            "Now you can identify your electrical and electronic components using "
        )
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(stringResource(id = R.string.app_name))
        }
        append(". This uses machine learning to produce component information with a high accuracy.")
    }

    Surface(modifier = modifier) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 32.dp,
                        horizontal = 8.dp
                    ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 0.dp,
                    focusedElevation = 2.dp,
                    hoveredElevation = 0.dp,
                )
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Box(
                        modifier = modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = message,
                            fontSize = 14.sp,
                            color = LocalBlack,
                            maxLines = 5,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    Spacer(modifier = modifier.height(32.dp))

                    Row(
                        modifier = modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TertiaryButton(
                            label = stringResource(id = R.string.abort),
                            onClick = onAbort,
                        )

                        SecondaryButton(
                            label = stringResource(id = R.string.get_started),
                            onClick = { cameraPermissionState.launchPermissionRequest() }
                        )
                    }
                }
            }
        }
    }

}