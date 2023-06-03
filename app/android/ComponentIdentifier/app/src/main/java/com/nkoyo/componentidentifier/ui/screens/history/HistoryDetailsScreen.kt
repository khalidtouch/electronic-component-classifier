package com.nkoyo.componentidentifier.ui.screens.history

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.domain.extensions.asString


@Composable
fun HistoryDetailsScreen() {
    LazyColumn(Modifier.fillMaxSize()) {
//        image()
//        info()
    }
}

private fun LazyListScope.image(detail: HistoryDetailItem) {
    item {
        AsyncImage(
            modifier = Modifier,
            model = ImageRequest.Builder(LocalContext.current)
                .data(detail.imageUri)
                .build(),
            contentDescription = stringResource(id = R.string.saved_photo),
        )
        Spacer(Modifier.height(12.dp))
    }
}

private fun LazyListScope.info(
    detail: HistoryDetailItem,
) {
    item {
        Column {
            Text(
                text = detail.historyItem.componentName,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                )
            )
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = detail.historyItem.value,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = detail.historyItem.dateTime.asString(),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
        Spacer(Modifier.height(12.dp))
    }
}


data class HistoryDetailItem(
    val historyItem: HistoryItem,
    val imageUri: Uri,
)