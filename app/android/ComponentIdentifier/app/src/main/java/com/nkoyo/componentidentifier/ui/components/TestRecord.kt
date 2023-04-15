package com.nkoyo.componentidentifier.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class TestRecord(
    val topic: String,
    val probability: String,
)

@Composable
fun TestRecordScreen(
    modifier: Modifier = Modifier,
    testRecord: TestRecord,
) {
    Surface(
        modifier = modifier,
        color = Color.Transparent
    ) {
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = testRecord.topic,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = testRecord.probability,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
@Preview
private fun TestRecordScreenPreview(){
    TestRecordScreen(
        testRecord = TestRecord(
            topic = "topic",
            probability = "0.78"
        )
    )
}

