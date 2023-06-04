package com.nkoyo.componentidentifier.ui.screens.web

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.ui.components.CircleIconButton


@Composable
fun WebInfoScreen(
    url: String,
    onClose: () -> Unit,
) {
    Box(Modifier.fillMaxSize()){
        AndroidView(
            factory = {  context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                loadUrl(url)
            }
        },
            update = { webView ->
                webView.loadUrl(url)
            }
        )

        Box(
            Modifier
                .fillMaxSize()
                .padding(8.dp), contentAlignment = Alignment.TopEnd) {
            CircleIconButton(
                icon = R.drawable.icon_close,
                contentDescription = stringResource(id = R.string.close),
                rotationAngle = 0f,
                onClick = onClose,
            )
        }
    }
}