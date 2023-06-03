package com.nkoyo.componentidentifier.ui.screens.main

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.ui.components.CircleIconButton
import com.nkoyo.componentidentifier.ui.components.TestRecord
import com.nkoyo.componentidentifier.ui.components.TestRecordScreen


@Composable
fun StaticBottomSheet(
    modifier: Modifier = Modifier,
    maxWidth: Dp,
    maxHeight: Dp,
    windowSizeClass: WindowSizeClass,
    rotationAngle: Float,
    context: Context = LocalContext.current,
    minimized: Boolean = false,
    onScale: () -> Unit = {},
    testRecords: List<TestRecord> =
        listOf(
            TestRecord(
                topic = context.getString(R.string.prediction_one),
                probability = "classifiedResults[context.getString(R.string.prediction_one)].orEmpty()"
            ),
            TestRecord(
                topic = context.getString(R.string.probability_one),
                probability = "classifiedResults[context.getString(R.string.probability_one)].orEmpty()"
            ),
            TestRecord(
                topic = context.getString(R.string.prediction_two),
                probability = "classifiedResults[context.getString(R.string.prediction_two)].orEmpty()"
            ),
            TestRecord(
                topic = context.getString(R.string.probability_two),
                probability = "classifiedResults[context.getString(R.string.probability_two)].orEmpty()"
            ),
            TestRecord(
                topic = context.getString(R.string.prediction_three),
                probability = "classifiedResults[context.getString(R.string.prediction_three)].orEmpty()"
            ),
            TestRecord(
                topic = context.getString(R.string.probability_three),
                probability = "classifiedResults[context.getString(R.string.probability_two)].orEmpty()"
            ),
        )
) {
    val calculatedHeight = if(windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
        maxHeight.times(0.4f)
    } else maxHeight
    val height by  animateDpAsState(targetValue = if (minimized) 32.dp else calculatedHeight)
    val width by animateDpAsState(targetValue = if (minimized) maxWidth.times(0.2f) else maxWidth)

    Card(
        modifier = modifier
            .height(height)
            .width(width),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            focusedElevation = 2.dp,
        )
    ) {
        if(!minimized) {
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
                            color = MaterialTheme.colorScheme.outline.copy(0.6f),
                            shape = CircleShape
                        ),
                )
            }

            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), contentAlignment = Alignment.CenterEnd
            ) {
                CircleIconButton(
                    icon = if (minimized) R.drawable.icon_maximize else R.drawable.icon_minimize,
                    contentDescription = stringResource(id = R.string.scale_button),
                    onClick = onScale,
                    surfaceColor = Color.Transparent,
                    borderColor = Color.Transparent,
                    rotationAngle = rotationAngle,
                )
            }

            Spacer(modifier = modifier.height(12.dp))

            testRecords.forEach { testRecord ->
                TestRecordScreen(
                    testRecord = testRecord,
                    modifier = modifier.padding(horizontal = 16.dp, vertical = 2.dp),
                )
            }
        } else {
            Box(
                modifier = modifier
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                CircleIconButton(
                    icon = R.drawable.icon_maximize,
                    contentDescription = stringResource(id = R.string.scale_button),
                    onClick = onScale,
                    surfaceColor = Color.Transparent,
                    borderColor = Color.Transparent,
                    rotationAngle = rotationAngle,
                )
            }
        }

    }
}