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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.ui.components.SecondaryButton
import com.nkoyo.componentidentifier.ui.components.TertiaryButton
import com.nkoyo.componentidentifier.ui.theme.LocalBackgroundTheme


@Composable
fun GettingStartedContent(
    modifier: Modifier = Modifier,
    onGettingApplicationStarted: () -> Unit,
    windowSizeClass: WindowSizeClass,
    fullWidth: Dp,
    onAbort: () -> Unit = {},
) {
    val valueMessage = buildAnnotatedString {
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
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = LocalBackgroundTheme.current.surfaceVariant,
                    contentColor = LocalBackgroundTheme.current.onSurfaceVariant,
                ),
                modifier = modifier
                    .width(
                        when (windowSizeClass.widthSizeClass) {
                            WindowWidthSizeClass.Compact -> {
                                fullWidth - 16.dp
                            }

                            WindowWidthSizeClass.Medium,
                            WindowWidthSizeClass.Expanded -> {
                                fullWidth - 300.dp
                            }

                            else -> fullWidth - 16.dp
                        }
                    )
                    .padding(
                        top = 16.dp,
                        bottom = 16.dp,
                        start = 8.dp,
                        end = 8.dp
                    ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 0.dp,
                    focusedElevation = 2.dp,
                    hoveredElevation = 0.dp,
                )
            ) {
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        //developer
                        Text(
                            text = stringResource(id = R.string.developer_name),
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = LocalBackgroundTheme.current.onSurfaceVariant,
                            )
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = stringResource(id = R.string.mat_no),
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = LocalBackgroundTheme.current.onSurfaceVariant,
                            )
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = stringResource(id = R.string.developer_location),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = LocalBackgroundTheme.current.onSurfaceVariant,
                            )
                        )
                        Spacer(Modifier.height(72.dp))
                        Box(
                            modifier = modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = valueMessage,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = LocalBackgroundTheme.current.onSurfaceVariant,
                                ),
                            )
                        }

                        Spacer(modifier = modifier.height(52.dp))

                        Row(
                            modifier = modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TertiaryButton(
                                contentColor =LocalBackgroundTheme.current.onSurfaceVariant,
                                label = stringResource(id = R.string.abort),
                                onClick = onAbort,
                            )

                            SecondaryButton(
                                containerColor = LocalBackgroundTheme.current.onSurfaceVariant,
                                contentColor = LocalBackgroundTheme.current.onSurfaceVariant,
                                label = stringResource(id = R.string.get_started),
                                onClick = onGettingApplicationStarted,
                            )
                        }
                    }
                }
            }
        }
    }

}