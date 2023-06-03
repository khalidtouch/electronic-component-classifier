package com.nkoyo.componentidentifier.ui.screens.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nkoyo.componentidentifier.domain.extensions.asString
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

@Composable
fun HistoryListScreen(
    historyItems: List<HistoryItem>,
    onClick: (HistoryItem) -> Unit
) {
    LazyColumn(
        Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 32.dp,
            bottom = 16.dp,
        )
    ) {
        historyItems.map { history ->
            historyListItem(history, onClick = onClick)
        }
    }
}


private fun LazyListScope.historyListItem(
    item: HistoryItem,
    onClick: (HistoryItem) -> Unit,
) {
    item {
        ListItemWrapper(onClick = { onClick(item) }) {
            Column {
                Text(
                    text = item.componentName,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                    )
                )
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = item.value,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = item.dateTime.asString(),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

            }
        }
        Spacer(Modifier.height(12.dp))
    }
}


@Composable
private fun ListItemWrapper(
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = Modifier.clickable(
            enabled = true,
            onClick = onClick,
            role = Role.Button,
        ),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.outline,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.secondary
        )
    ) {
        var rowHeight by remember { mutableStateOf(0) }

        Box(
            Modifier
                .fillMaxWidth()
                .onGloballyPositioned {
                    rowHeight = it.size.height
                }) {
            Box(
                Modifier
                    .background(color = MaterialTheme.colorScheme.secondary)
                    .width(16.dp)
                    .height(
                        with(LocalDensity.current) {
                            rowHeight.toDp()
                        }
                    )
            )

            Box(
                Modifier
                    .padding(start = 32.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth()
            ) {
                content()
            }
        }
    }
}


data class HistoryItem(
    val componentName: String,
    val value: String,
    val dateTime: LocalDateTime,
)