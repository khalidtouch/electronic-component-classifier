package com.nkoyo.componentidentifier.ui.screens.web

import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.ui.components.CircleIconButton
import com.nkoyo.componentidentifier.ui.theme.LocalBackgroundTheme
import com.nkoyo.componentidentifier.ui.viewmodel.MainViewModel


@Suppress("DEPRECATION")
@Composable
fun WebInfoScreen(
    onClose: () -> Unit,
    mainViewModel: MainViewModel,
) {
    val TAG = "WebInfo"
    val url by mainViewModel.selectedUrl.collectAsStateWithLifecycle()
    Log.e(TAG, "WebInfoScreen: the url is $url")

    Box(Modifier.fillMaxSize()){
        AndroidView(
            factory = {  context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                if(WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                    WebSettingsCompat.setForceDark(
                        this.settings,
                        WebSettingsCompat.FORCE_DARK_ON
                    )
                }
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
                .padding(8.dp)
                .background(color = Color.Transparent), contentAlignment = Alignment.TopEnd) {
            CircleIconButton(
                icon = R.drawable.icon_close,
                contentDescription = stringResource(id = R.string.close),
                rotationAngle = 0f,
                onClick = onClose,
            )
        }
    }
}