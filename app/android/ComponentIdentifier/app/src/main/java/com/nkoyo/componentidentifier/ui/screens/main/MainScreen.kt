package com.nkoyo.componentidentifier.ui.screens.main

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.view.OrientationEventListener
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.nkoyo.componentidentifier.domain.extensions.executor
import com.nkoyo.componentidentifier.domain.extensions.takeSnapshot
import com.nkoyo.componentidentifier.domain.usecases.ImageCaptureFlashMode
import com.nkoyo.componentidentifier.network.linker6
import com.nkoyo.componentidentifier.ui.viewmodel.EMPTY_FILE
import com.nkoyo.componentidentifier.ui.viewmodel.HighestProbabilityComponent
import com.nkoyo.componentidentifier.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDateTime
import java.util.concurrent.ExecutorService


@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    mainViewModel: MainViewModel,
    onAbortApplication: () -> Unit = {},
    onViewRecords: () -> Unit,
    onPreviewWebInfo: () -> Unit,
    emptyImageUri: Uri = Uri.parse("file://dev/null"),
) {
    val TAG = "MainScreen"
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var rotationAngle by remember { mutableStateOf(0f) }
    val imageUri = remember { mutableStateOf(emptyImageUri) }
    val highestProbabilityComponentLabel by mainViewModel.highestProbabilityComponentLabel.collectAsStateWithLifecycle()
    val savedImageUri by mainViewModel.savedImageUri.collectAsStateWithLifecycle()
    val progressWheelState by mainViewModel.progressWheelState.collectAsStateWithLifecycle()
    val cameraSelector by mainViewModel.cameraSelector.collectAsStateWithLifecycle()
    val cameraExecutorService = mainViewModel.cameraExecutor
    val flashlightExecutorService = mainViewModel.flashLightExecutor
    val flashlightState by mainViewModel.flashLightState.collectAsStateWithLifecycle()
    val gettingStartedState by mainViewModel.gettingStartedState.collectAsStateWithLifecycle()
    val bottomSheetMinimized by mainViewModel.bottomSheetMinimized.collectAsStateWithLifecycle()
    val classificationState by mainViewModel.classificationState.collectAsStateWithLifecycle()
    val result by mainViewModel.result.collectAsStateWithLifecycle()
    val resultBuffer by mainViewModel.resultBuffer.collectAsStateWithLifecycle()

    val orientationEventListener by lazy {
        object : OrientationEventListener(context) {
            override fun onOrientationChanged(orientation: Int) {
                rotationAngle = orientation.toFloat()
            }

        }
    }

    DisposableEffect(Unit) {
        orientationEventListener.enable()
        onDispose { orientationEventListener.disable() }
    }

    val onSavePhotoFile: (File) -> Unit = {
        imageUri.value = it.toUri()
        mainViewModel.registerHistory(
            componentName = highestProbabilityComponentLabel,
            imageUrl = imageUri.value.toString(),
        )
    }

    MainScreen(
        imageUri = savedImageUri,
        emptyImageUri = Uri.parse(EMPTY_FILE),
        rotationAngle = rotationAngle,
        windowSizeClass = windowSizeClass,
        clearSavedUri = mainViewModel::clearSavedUri,
        initializeApp = {
            mainViewModel.onBottomSheetMinimizedChanged(true)
            mainViewModel.updateClassificationState(true)
        },
        onTakeSnapshot = { imageCapture ->
            mainViewModel.updateResultLabel(result.label.uppercase())
            coroutineScope.launch {
                imageCapture.takeSnapshot(context.executor)
                    .let(onSavePhotoFile)
            }
        },
        info = ComponentInfo(
            componentName = resultBuffer.label.uppercase(),
            description = linker6[resultBuffer.label]?.second.orEmpty(),
            url = linker6[resultBuffer.label]?.first.orEmpty(),
            dateTime = LocalDateTime.now(),
        ),
        onToggleFlashLight = mainViewModel::onFlashLightStateChanged,
        onToggleCamera = { mainViewModel.onToggleCameraSelector(cameraSelector) },
        onBufferResult = mainViewModel::onBufferResult,
        progressWheelState = progressWheelState,
        onViewRecords = onViewRecords,
        onPreviewWebInfo = onPreviewWebInfo,
        onGettingApplicationStarted = mainViewModel::onApplicationStarted,
        onAbort = onAbortApplication,
        cameraSelector = cameraSelector,
        cameraExecutorService = cameraExecutorService,
        flashLightState = flashlightState,
        flashlightExecutorService = flashlightExecutorService,
        produceResult = mainViewModel::classifyAndProduceHighestProbabilityLabel,
        updateResult = mainViewModel::updateResult,
        gettingStartedState = gettingStartedState,
        bottomSheetMinimized = bottomSheetMinimized,
        classificationState = classificationState,
        minimizeBottomSheet = { mainViewModel.onBottomSheetMinimizedChanged(true) },
        expandBottomSheet = { mainViewModel.onBottomSheetMinimizedChanged(false) },
        onScale = { mainViewModel.updateClassificationState(!classificationState) },
        openUrl = mainViewModel::openWebUrl,
        result = result,
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun MainScreen(
    imageUri: Uri,
    emptyImageUri: Uri,
    rotationAngle: Float,
    clearSavedUri: () -> Unit,
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
    produceResult: ((Bitmap) -> HighestProbabilityComponent)? = null,
    updateResult: (HighestProbabilityComponent) -> Unit,
    initializeApp: () -> Unit,
    result: HighestProbabilityComponent,
    minimizeBottomSheet: () -> Unit,
    expandBottomSheet: () -> Unit,
    onBufferResult: (HighestProbabilityComponent) -> Unit,
) {
    if (imageUri != emptyImageUri) {
        PhotoCaptureScreen(
            imageUri = imageUri,
            rotationAngle = rotationAngle,
            onRemove = clearSavedUri
        )
    } else {
        MainPreviewScreen(
            modifier = Modifier.testTag("main:MainPreviewScreenWrapper"),
            windowSizeClass = windowSizeClass,
            onPreviewWebInfo = onPreviewWebInfo,
            initializeApp = initializeApp,
            onTakeSnapshot = onTakeSnapshot,
            minimizeBottomSheet = minimizeBottomSheet,
            expandBottomSheet = expandBottomSheet,
            onViewRecords = onViewRecords,
            cameraExecutorService = cameraExecutorService,
            cameraSelector = cameraSelector,
            flashlightExecutorService = flashlightExecutorService,
            flashLightState = flashLightState,
            bottomSheetMinimized = bottomSheetMinimized,
            classificationState = classificationState,
            gettingStartedState = gettingStartedState,
            result = result,
            progressWheelState = progressWheelState,
            onBufferResult = onBufferResult,
            info = info,
            produceResult = checkNotNull(produceResult),
            onToggleCamera = onToggleCamera,
            onScale = onScale,
            openUrl = openUrl,
            onToggleFlashLight = onToggleFlashLight,
            onGettingApplicationStarted = onGettingApplicationStarted,
            onAbort = onAbort,
            updateResult = updateResult,
        )
    }
}