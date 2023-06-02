package com.nkoyo.componentidentifier.ui.screens.main

import android.util.Log
import android.view.OrientationEventListener
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.common.util.concurrent.ListenableFuture
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.domain.extensions.executor
import com.nkoyo.componentidentifier.domain.extensions.getCameraProvider
import com.nkoyo.componentidentifier.domain.extensions.takeSnapshot
import com.nkoyo.componentidentifier.domain.usecases.CameraPreviewUseCase
import com.nkoyo.componentidentifier.domain.usecases.ImageAnalysisUseCase
import com.nkoyo.componentidentifier.domain.usecases.ImageCaptureFlashMode
import com.nkoyo.componentidentifier.domain.usecases.ImageCaptureUseCase
import com.nkoyo.componentidentifier.ui.components.TestRecord
import com.nkoyo.componentidentifier.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainPreviewScreen(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    mainViewModel: MainViewModel = hiltViewModel<MainViewModel>(),
    navController: NavHostController,
    onAbortApplication: () -> Unit = {},
    onSavePhotoFile: (File) -> Unit = {},
    cameraPermissionState: PermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    ),
    gettingStartedContent: @Composable (
        fullWidth: Dp,
    ) -> Unit = { fullWidth ->
        GettingStartedContent(
            onAbort = onAbortApplication,
            onGettingApplicationStarted = {
                if (!cameraPermissionState.status.isGranted) cameraPermissionState.launchPermissionRequest()
                mainViewModel.onApplicationStarted()
            },
            fullWidth = fullWidth,
            windowSizeClass = windowSizeClass,
        )
    },
    content: @Composable (
        flashLightState: ImageCaptureFlashMode,
        onToggleFlashLight: () -> Unit,
        onTakeSnapshot: () -> Unit,
        onToggleCamera: () -> Unit,
        rotationAngle: Float,
    ) -> Unit = { flashlightState, onToggleFlashLight, onTakeSnapshot, onToggleCamera, rotationAngle ->
        MainPreviewScreenContent(
            rotationAngle = rotationAngle,
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
    val configuration = LocalConfiguration.current
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraSelector by mainViewModel.cameraSelector.collectAsState()
    val cameraExecutor = mainViewModel.cameraExecutor
    val flashLightExecutor = mainViewModel.flashLightExecutor
    val testRecords by mainViewModel.testRecords.collectAsState()
    val bottomSheetMinimized by mainViewModel.bottomSheetMinimized.collectAsState()
    val gettingStartedState by mainViewModel.gettingStartedState.collectAsStateWithLifecycle()
    var rotationAngle by remember { mutableStateOf(0f) }

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

    val orientationEventListener by lazy {
        object : OrientationEventListener(context) {
            override fun onOrientationChanged(orientation: Int) {
                rotationAngle = orientation.toFloat()
                Log.e(TAG, "onOrientationChanged: current orientation value is $orientation")
            }

        }
    }

    val flashLightState by mainViewModel.flashLightState.collectAsState()

    val cameraPreviewUseCase = remember { mutableStateOf(CameraPreviewUseCase().of(previewView)) }
    val imageCaptureUseCase =
        remember(flashLightState) { mutableStateOf(ImageCaptureUseCase().of(flashLightState)) }
    val imageAnalysisUseCase = remember { mutableStateOf(ImageAnalysisUseCase().of()) }

    DisposableEffect(Unit) {
        Log.e(TAG, "MainPreviewScreen: DiposableEffect has been called")
        orientationEventListener.enable()
        onDispose { orientationEventListener.disable() }
    }

    LaunchedEffect(Unit, cameraPreviewUseCase.value, cameraSelector, flashLightState) {
        Log.e(TAG, "MainPreviewScreen: refresh camera use-case bindings")
        val provider = context.getCameraProvider()

        imageAnalysisUseCase.value.setAnalyzer(
            cameraExecutor,
            ImageAnalysis.Analyzer { imageProxy ->
                Log.e(TAG, "MainPreviewScreen: image is being analyzed")
                val generatedBitmap = imageProxy.toBitmap() ?: return@Analyzer
                val classifiedResults = mainViewModel.classify(generatedBitmap)
                Log.e(
                    TAG,
                    "MainPreviewScreen: the classified results are : ${classifiedResults.toString()}"
                )
                mainViewModel.onTestRecordsChanged(
                    listOf(
                        TestRecord(
                            topic = context.getString(R.string.prediction_one),
                            probability = classifiedResults[context.getString(R.string.prediction_one)].orEmpty()
                        ),
                        TestRecord(
                            topic = context.getString(R.string.probability_one),
                            probability = classifiedResults[context.getString(R.string.probability_one)].orEmpty()
                        ),
                        TestRecord(
                            topic = context.getString(R.string.prediction_two),
                            probability = classifiedResults[context.getString(R.string.prediction_two)].orEmpty()
                        ),
                        TestRecord(
                            topic = context.getString(R.string.probability_two),
                            probability = classifiedResults[context.getString(R.string.probability_two)].orEmpty()
                        ),
                        TestRecord(
                            topic = context.getString(R.string.prediction_three),
                            probability = classifiedResults[context.getString(R.string.prediction_three)].orEmpty()
                        ),
                        TestRecord(
                            topic = context.getString(R.string.probability_two),
                            probability = classifiedResults[context.getString(R.string.probability_two)].orEmpty()
                        ),
                    )
                )

                imageProxy.close()
                Log.e(TAG, "MainPreviewScreen: testRecord size is ${testRecords.size}")
            })

        try {
            provider.unbindAll()
            val camera = provider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                cameraPreviewUseCase.value,
                imageCaptureUseCase.value,
                imageAnalysisUseCase.value,
            )
            val cameraControl = camera.cameraControl
            val torch: ListenableFuture<Void> =
                cameraControl.enableTorch(flashLightState == ImageCaptureFlashMode.On)
            torch.addListener({
                try {
                    torch.get()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, flashLightExecutor)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    Surface(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val maxWidth = maxWidth
            val maxHeight = maxHeight

            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            )

            if (gettingStartedState) {
                gettingStartedContent(
                    fullWidth = configuration.screenWidthDp.dp,
                )
            } else {
                content(
                    flashLightState = flashLightState,
                    rotationAngle = rotationAngle,
                    onToggleFlashLight = {
                        when (flashLightState) {
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
                        if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                            mainViewModel.onCameraSelectorChanged(CameraSelector.DEFAULT_FRONT_CAMERA)
                        } else {
                            mainViewModel.onCameraSelectorChanged(CameraSelector.DEFAULT_BACK_CAMERA)
                        }
                    }
                )
            }

            //static bottom sheet
            AnimatedVisibility(
                visible = false,
                enter = slideInVertically(
                    animationSpec = tween(
                        durationMillis = 2,
                        delayMillis = 2,
                    ),
                    initialOffsetY = { -it }
                ),
                exit = slideOutVertically(
                    animationSpec = tween(durationMillis = 2),
                    targetOffsetY = { -it })
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp), contentAlignment =
                    if (bottomSheetMinimized) Alignment.TopCenter else
                        Alignment.BottomCenter
                ) {
                    StaticBottomSheet(
                        maxWidth = maxWidth,
                        maxHeight = maxHeight,
                        rotationAngle = rotationAngle,
                        minimized = bottomSheetMinimized,
                        testRecords = testRecords,
                        onScale = { mainViewModel.onBottomSheetMinimizedChanged(!bottomSheetMinimized) }
                    )
                }
            }
        }
    }
}