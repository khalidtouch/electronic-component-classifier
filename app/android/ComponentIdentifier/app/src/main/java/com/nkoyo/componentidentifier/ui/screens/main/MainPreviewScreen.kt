package com.nkoyo.componentidentifier.ui.screens

import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.ui.screens.main.MainPreviewScreenContent
import com.nkoyo.componentidentifier.ui.screens.main.PermissionNotAvailableContent


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainPreviewScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    cameraPermissionState: PermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    ),
    permissionRationale: String = stringResource(id = R.string.permission_is_important),
    permissionNotAvailableContent: @Composable () -> Unit = {
         PermissionNotAvailableContent(cameraPermissionState = cameraPermissionState)
    },
    content: @Composable () -> Unit = { MainPreviewScreenContent() },
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    val previewView: PreviewView = remember {
        val view = PreviewView(context).apply {
            this.scaleType = PreviewView.ScaleType.FILL_CENTER
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        view
    }

    val cameraSelector: MutableState<CameraSelector> = remember {
        mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA)
    }


    Surface(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            )

            if (!cameraPermissionState.status.isGranted) {
                permissionNotAvailableContent()
            } else {
                content()
            }

        }
    }
}