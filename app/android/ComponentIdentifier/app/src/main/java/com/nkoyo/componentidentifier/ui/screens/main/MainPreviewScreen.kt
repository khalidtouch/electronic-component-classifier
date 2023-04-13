package com.nkoyo.componentidentifier.ui.screens.main

import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.domain.extensions.executor
import com.nkoyo.componentidentifier.domain.extensions.getCameraProvider
import com.nkoyo.componentidentifier.domain.extensions.takeSnapshot
import com.nkoyo.componentidentifier.domain.usecases.CameraPreviewUseCase
import com.nkoyo.componentidentifier.domain.usecases.ImageCaptureFlashMode
import com.nkoyo.componentidentifier.domain.usecases.ImageCaptureUseCase
import com.nkoyo.componentidentifier.ui.theme.LocalBlack
import com.nkoyo.componentidentifier.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainPreviewScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel<MainViewModel>(),
    navController: NavHostController,
    onAbortApplication: () -> Unit = {},
    onSavePhotoFile: (File) -> Unit = {},
    cameraPermissionState: PermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    ),
    permissionNotAvailableContent: @Composable () -> Unit = {
        PermissionNotAvailableContent(
            cameraPermissionState = cameraPermissionState,
            onAbort = onAbortApplication
        )
    },
    content: @Composable (
        flashLightState: ImageCaptureFlashMode,
        onToggleFlashLight: () -> Unit,
        onTakeSnapshot: () -> Unit,
        onToggleCamera: () -> Unit,
    ) -> Unit = { flashlightState, onToggleFlashLight, onTakeSnapshot, onToggleCamera ->
        MainPreviewScreenContent(
            flashLightState = flashlightState,
            onToggleFlashLight = onToggleFlashLight,
            onTakeSnapshot = onTakeSnapshot,
            onToggleCamera = onToggleCamera,
            onClose = onAbortApplication
        )
    },
) {
    val TAG = "MainPreviewScreen"
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraSelector by mainViewModel.cameraSelector.collectAsState()

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

    val flashLightState by mainViewModel.flashLightState.collectAsState()

    val cameraPreviewUseCase = remember { mutableStateOf(CameraPreviewUseCase().of(previewView)) }
    val imageCaptureUseCase = remember { mutableStateOf(ImageCaptureUseCase().of(flashLightState)) }

    LaunchedEffect(Unit, cameraPreviewUseCase.value) {
        val provider = context.getCameraProvider()
        try {
            provider.unbindAll()
            provider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                cameraPreviewUseCase.value,
                imageCaptureUseCase.value,
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    Surface(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            )
            Log.e(TAG, "MainPreviewScreen: Preview Screen")

            if (!cameraPermissionState.status.isGranted) {
                permissionNotAvailableContent()
            } else {
                content(
                    flashLightState = flashLightState,
                    onToggleFlashLight = {
                        when(flashLightState){
                            //  circular control
                            is ImageCaptureFlashMode.Auto -> {
                                mainViewModel.onFlashLightStateChanged(ImageCaptureFlashMode.On)
                            }
                            is ImageCaptureFlashMode.On -> {
                                mainViewModel.onFlashLightStateChanged(ImageCaptureFlashMode.Off)
                            }
                            is ImageCaptureFlashMode.Off -> {
                                mainViewModel.onFlashLightStateChanged(ImageCaptureFlashMode.Auto)
                            }
                        }
                    },
                    onTakeSnapshot = {
                        coroutineScope.launch {
                            imageCaptureUseCase.value.takeSnapshot(context.executor)
                                .let(onSavePhotoFile)
                        }
                    },
                    onToggleCamera = {
                        if(cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA){
                            mainViewModel.onCameraSelectorChanged(CameraSelector.DEFAULT_FRONT_CAMERA)
                        } else {
                            mainViewModel.onCameraSelectorChanged(CameraSelector.DEFAULT_BACK_CAMERA)
                        }
                    }
                )
            }

        }
    }
}