package com.nkoyo.componentidentifier.ui.screens.main

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.nkoyo.componentidentifier.ui.theme.LocalBlack
import java.io.File


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onAbortApplication: () -> Unit = {},
    navController: NavHostController,
    emptyImageUri: Uri = Uri.parse("file://dev/null"),
    captureContent: @Composable (imageUri: MutableState<Uri>, onRemove: () -> Unit) -> Unit = { uriState, onRemove ->
        PhotoCaptureScreen(
            imageUri = uriState,
            onRemove = onRemove
        )
    },
    previewContent: @Composable (onSavePhotoFile: (File) -> Unit) -> Unit = { onSavePhotoFile ->
        MainPreviewScreen(
            navController = navController,
            onAbortApplication = onAbortApplication,
            onSavePhotoFile = onSavePhotoFile,
        )
    }
) {
    val TAG = "MainScreen"
    val imageUri = remember { mutableStateOf(emptyImageUri) }
    Log.e(TAG, "MainScreen: the value of uri is ${imageUri.value}", )

    if (imageUri.value != emptyImageUri) {
        Log.e(TAG, "MainScreen: image uri is NOT empty")
        captureContent(
            imageUri = imageUri,
            onRemove = { imageUri.value = emptyImageUri }
        )
    } else {
        Log.e(TAG, "MainScreen: image uri is empty")
        previewContent(
            onSavePhotoFile = {
                imageUri.value = it.toUri()
            }
        )
    }
}