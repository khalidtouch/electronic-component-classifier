package com.nkoyo.componentidentifier.ui.screens.main

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.view.OrientationEventListener
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
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
import com.nkoyo.componentidentifier.network.linker6
import com.nkoyo.componentidentifier.ui.viewmodel.HighestProbabilityComponent
import com.nkoyo.componentidentifier.ui.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception
import java.time.LocalDateTime
import java.util.concurrent.ExecutorService


@SuppressLint("UnsafeOptInUsageError")
@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainPreviewScreen(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    gettingStartedState: Boolean,
    flashLightState: ImageCaptureFlashMode,
    onTakeSnapshot: (ImageCapture) -> Unit,
    onToggleCamera: () -> Unit,
    bottomSheetMinimized: Boolean,
    classificationState: Boolean,
    info: ComponentInfo,
    cameraSelector: CameraSelector,
    onScale: () -> Unit,
    openUrl: (String) -> Unit,
    progressWheelState: Boolean,
    onPreviewWebInfo: () -> Unit,
    onViewRecords: () -> Unit,
    onToggleFlashLight: (ImageCaptureFlashMode) -> Unit,
    onGettingApplicationStarted: () -> Unit,
    onAbort: () -> Unit,
    cameraExecutorService: ExecutorService,
    flashlightExecutorService: ExecutorService,
    produceResult: (Bitmap) -> HighestProbabilityComponent,
    updateResult: (HighestProbabilityComponent) -> Unit,
    initializeApp: () -> Unit,
    result: HighestProbabilityComponent,
    minimizeBottomSheet: () -> Unit,
    expandBottomSheet: () -> Unit,
    onBufferResult: (HighestProbabilityComponent) -> Unit,
    cameraPermissionState: PermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    ),
) {
    val TAG = "MainPreviewScreen"
    val configuration = LocalConfiguration.current

    MainPreviewScreen(
        modifier = modifier,
        initializeApp = initializeApp,
        windowSizeClass = windowSizeClass,
        gettingStartedState = gettingStartedState,
        flashLightState = flashLightState,
        bottomSheetMinimized = bottomSheetMinimized,
        classificationState = classificationState,
        cameraSelector = cameraSelector,
        maxHeight = configuration.screenHeightDp.dp,
        maxWidth = configuration.screenWidthDp.dp,
        progressWheelState = progressWheelState,
        flashlightExecutorService = flashlightExecutorService,
        cameraExecutorService = cameraExecutorService,
        info = info,
        onTakeSnapshot = onTakeSnapshot,
        onToggleFlashLight = onToggleFlashLight,
        onToggleCamera = onToggleCamera,
        onViewRecords = onViewRecords,
        openUrl = openUrl,
        onScale = onScale,
        onPreviewWebInfo = onPreviewWebInfo,
        onGettingApplicationStarted = {
            if (!cameraPermissionState.status.isGranted) cameraPermissionState.launchPermissionRequest()
            onGettingApplicationStarted()
        },
        onAbort = onAbort,
        produceResult = produceResult,
        expandBottomSheet = expandBottomSheet,
        minimizeBottomSheet = minimizeBottomSheet,
        updateResult = updateResult,
        result = result,
        onBufferResult = onBufferResult,
    )
}


@Composable
private fun MainPreviewScreen(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    gettingStartedState: Boolean,
    flashLightState: ImageCaptureFlashMode,
    onTakeSnapshot: (ImageCapture) -> Unit,
    onToggleCamera: () -> Unit,
    bottomSheetMinimized: Boolean,
    classificationState: Boolean,
    info: ComponentInfo,
    cameraSelector: CameraSelector,
    maxWidth: Dp,
    maxHeight: Dp,
    onScale: () -> Unit,
    openUrl: (String) -> Unit,
    progressWheelState: Boolean,
    onPreviewWebInfo: () -> Unit,
    onViewRecords: () -> Unit,
    onToggleFlashLight: (ImageCaptureFlashMode) -> Unit,
    onGettingApplicationStarted: () -> Unit,
    onAbort: () -> Unit,
    cameraExecutorService: ExecutorService,
    flashlightExecutorService: ExecutorService,
    produceResult: (Bitmap) -> HighestProbabilityComponent,
    updateResult: (HighestProbabilityComponent) -> Unit,
    initializeApp: () -> Unit,
    result: HighestProbabilityComponent,
    minimizeBottomSheet: () -> Unit,
    expandBottomSheet: () -> Unit,
    onBufferResult: (HighestProbabilityComponent) -> Unit,
) {

    val context = LocalContext.current
    val TAG = "MainPreview"
    var rotationAngle by remember { mutableStateOf(0f) }
    val lifecycleOwner = LocalLifecycleOwner.current

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

    val cameraPreviewUseCase = remember { mutableStateOf(CameraPreviewUseCase().of(previewView)) }
    val imageCaptureUseCase =
        remember(flashLightState) { mutableStateOf(ImageCaptureUseCase().of(flashLightState)) }
    val imageAnalysisUseCase =
        remember { mutableStateOf(ImageAnalysisUseCase().of()) }

    val orientationEventListener by lazy {
        object : OrientationEventListener(context) {
            override fun onOrientationChanged(orientation: Int) {
                rotationAngle = orientation.toFloat()
                val cameraRotation = when (orientation) {
                    in 45 until 135 -> android.view.Surface.ROTATION_270
                    in 135 until 225 -> android.view.Surface.ROTATION_180
                    in 225 until 315 -> android.view.Surface.ROTATION_90
                    else -> android.view.Surface.ROTATION_0
                }
                imageAnalysisUseCase.value.targetRotation = cameraRotation
                imageCaptureUseCase.value.targetRotation = cameraRotation
            }

        }
    }


    LaunchedEffect(Unit, cameraPreviewUseCase.value, cameraSelector, flashLightState) {
        val provider = context.getCameraProvider()

        imageAnalysisUseCase.value.setAnalyzer(
            cameraExecutorService,
            ImageAnalysis.Analyzer { imageProxy ->
                val generatedBitmap = imageProxy.toBitmap() ?: return@Analyzer
                updateResult(produceResult(generatedBitmap))
                imageProxy.close()
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
            }, flashlightExecutorService)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    DisposableEffect(Unit) {
        initializeApp()
        orientationEventListener.enable()
        onDispose { orientationEventListener.disable() }
    }


    //process bottomsheet effect
    LaunchedEffect(
        result.label,
        gettingStartedState,
        classificationState,
        bottomSheetMinimized,
    ) {
        //control the bottom sheet data and visibility
        if (!classificationState) {
            minimizeBottomSheet()
        }

        if (gettingStartedState) return@LaunchedEffect
        if (result == HighestProbabilityComponent.Default) return@LaunchedEffect
        delay(3_000)
        if (classificationState && bottomSheetMinimized) {
            onBufferResult(result)
            expandBottomSheet()
            Log.e(TAG, "MainPreviewScreen: onBufferResult is called")
        }
    }


    Surface(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            )

            if (gettingStartedState) {
                GettingStartedContent(
                    fullWidth = maxWidth,
                    modifier = modifier,
                    onGettingApplicationStarted = onGettingApplicationStarted,
                    windowSizeClass = windowSizeClass,
                    onAbort = onAbort,
                    contentDesc = stringResource(id = R.string.getting_started_dialog)
                )
            }

            MainPreviewScreenContent(
                modifier = modifier,
                rotationAngle = rotationAngle,
                gettingStartedState = gettingStartedState,
                windowSizeClass = windowSizeClass,
                onAbort = onAbort,
                onToggleCamera = onToggleCamera,
                onToggleFlashLight = { onToggleFlashLight(flashLightState) },
                onTakeSnapshot = { onTakeSnapshot(imageCaptureUseCase.value) },
                onViewRecords = onViewRecords,
                flashLightState = flashLightState,
                maxHeight = maxHeight,
                maxWidth = maxWidth,
                openUrl = openUrl,
                bottomSheetMinimized = bottomSheetMinimized,
                info = info,
                onPreviewWebInfo = onPreviewWebInfo,
                onScale = onScale,
            )


            if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
                //dynamic bottom sheet
                AnimatedVisibility(
                    visible = !gettingStartedState,
                    enter = slideInVertically(
                        animationSpec = tween(
                            durationMillis = 200,
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
                        DynamicBottomSheet(
                            modifier = Modifier,
                            maxWidth = maxWidth,
                            maxHeight = maxHeight,
                            onPreviewWebInfo = onPreviewWebInfo,
                            openUrl = openUrl,
                            windowSizeClass = windowSizeClass,
                            rotationAngle = rotationAngle,
                            info = info,
                            minimized = bottomSheetMinimized,
                            onScale = onScale,
                            contentDesc = stringResource(id = R.string.bottom_sheet_compact)
                        )
                    }
                }
            }

            if (progressWheelState) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val progressIndicatorContentDesc =
                            stringResource(id = R.string.circular_progress_indicator)
                        CircularProgressIndicator(
                            modifier = Modifier
                                .height(28.dp)
                                .width(28.dp)
                                .semantics { contentDescription = progressIndicatorContentDesc },
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 4.dp,
                            trackColor = MaterialTheme.colorScheme.primary.copy(0.5f),
                            strokeCap = StrokeCap.Round,
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = stringResource(id = R.string.classifying),
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }
            }
        }
    }
}
