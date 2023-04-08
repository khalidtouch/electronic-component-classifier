package com.nkoyo.componentidentifier.ui.screens.main

import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.domain.extensions.getCameraProvider
import com.nkoyo.componentidentifier.domain.extensions.usecases.CameraPreviewUseCase
import kotlinx.coroutines.launch
import java.lang.Exception


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainPreviewScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onAbortApplication: () -> Unit,
    cameraPermissionState: PermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    ),
    permissionRationale: String = stringResource(id = R.string.permission_is_important),
    permissionNotAvailableContent: @Composable () -> Unit = {
        PermissionNotAvailableContent(
            cameraPermissionState = cameraPermissionState,
            onAbort = onAbortApplication
        )
    },
    content: @Composable (
        flashLightState: MutableState<Boolean>,
        onToggleFlashLight: () -> Unit,
    ) -> Unit = { flashlightState, onToggleFlashLight ->
        MainPreviewScreenContent(
            flashLightState = flashlightState,
            onToggleFlashLight = onToggleFlashLight
        )
    },
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    val cameraSelector: MutableState<CameraSelector> = remember {
        mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA)
    }
    val flashLightState = remember { mutableStateOf(false) }

    val previewView: PreviewView = remember {
        val view = PreviewView(context).apply {
            this.scaleType = PreviewView.ScaleType.FILL_CENTER
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        val cameraPreviewUseCase = CameraPreviewUseCase()

        coroutineScope.launch {
            val provider = context.getCameraProvider()
            try {
                provider.unbindAll()
                provider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector.value,
                    cameraPreviewUseCase.of(view)
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        view
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
                content(
                    flashLightState = flashLightState,
                    onToggleFlashLight = {
                        flashLightState.value = !flashLightState.value
                    }
                )
            }

        }
    }
}