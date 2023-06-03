package com.nkoyo.componentidentifier.ui.screens.main

import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.OrientationEventListener
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import java.io.File


@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    onAbortApplication: () -> Unit = {},
    onViewRecords: () -> Unit,
    navController: NavHostController,
    emptyImageUri: Uri = Uri.parse("file://dev/null"),
    captureContent: @Composable (imageUri: MutableState<Uri>, onRemove: () -> Unit, rotationAngle: Float) -> Unit = { uriState, onRemove, rotationAngle ->
        PhotoCaptureScreen(
            imageUri = uriState,
            onRemove = onRemove,
            rotationAngle = rotationAngle,
        )
    },
    previewContent: @Composable (onSavePhotoFile: (File) -> Unit) -> Unit = { onSavePhotoFile ->
        MainPreviewScreen(
            navController = navController,
            onAbortApplication = onAbortApplication,
            onSavePhotoFile = onSavePhotoFile,
            windowSizeClass = windowSizeClass,
            onViewRecords = onViewRecords,
        )
    }
) {
    val TAG = "MainScreen"
    val context = LocalContext.current
    var rotationAngle by remember { mutableStateOf(0f) }
    val imageUri = remember { mutableStateOf(emptyImageUri) }
    Log.e(TAG, "MainScreen: the value of uri is ${imageUri.value}")

    val orientationEventListener by lazy {
        object : OrientationEventListener(context) {
            override fun onOrientationChanged(orientation: Int) {
                rotationAngle = orientation.toFloat()
                Log.e(TAG, "onOrientationChanged: current orientation value is $orientation")
            }

        }
    }

    DisposableEffect(Unit) {
        orientationEventListener.enable()
        onDispose { orientationEventListener.disable() }
    }

    if (imageUri.value != emptyImageUri) {
        Log.e(TAG, "MainScreen: image uri is NOT empty")
        captureContent(
            imageUri = imageUri,
            rotationAngle = rotationAngle,
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