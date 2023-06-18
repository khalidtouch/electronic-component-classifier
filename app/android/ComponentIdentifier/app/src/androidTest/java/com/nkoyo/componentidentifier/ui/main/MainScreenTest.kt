package com.nkoyo.componentidentifier.ui.main

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.camera.core.CameraSelector
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.test.platform.app.InstrumentationRegistry
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.domain.extensions.takeSnapshot
import com.nkoyo.componentidentifier.domain.usecases.ImageCaptureFlashMode
import com.nkoyo.componentidentifier.ui.fakes.produceResult
import com.nkoyo.componentidentifier.ui.navigation.Route.MainScreen
import com.nkoyo.componentidentifier.ui.screens.main.ComponentInfo
import com.nkoyo.componentidentifier.ui.screens.main.MainScreen
import com.nkoyo.componentidentifier.ui.viewmodel.EMPTY_FILE
import com.nkoyo.componentidentifier.ui.viewmodel.HighestProbabilityComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.Executors


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalTestApi::class)
class MainScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    val context = InstrumentationRegistry.getInstrumentation().targetContext

    val produceFakeResult: (Bitmap?) -> HighestProbabilityComponent = { produceResult(null) }


    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun gettingStartedDialog_whenAppIsStarting_isDisplayed() {
        composeTestRule.setContent {
            BoxWithConstraints {
                MainScreen(
                    emptyImageUri = Uri.parse(EMPTY_FILE),
                    imageUri = Uri.parse(EMPTY_FILE),
                    gettingStartedState = true,
                    rotationAngle = 0f,
                    clearSavedUri = {},
                    windowSizeClass = calculateWindowSizeClass(activity = composeTestRule.activity),
                    flashLightState = ImageCaptureFlashMode.Off,
                    onTakeSnapshot = {},
                    onToggleFlashLight = {},
                    onToggleCamera = {},
                    bottomSheetMinimized = false,
                    classificationState = true,
                    cameraExecutorService = Executors.newSingleThreadExecutor(),
                    flashlightExecutorService = Executors.newSingleThreadExecutor(),
                    info = ComponentInfo.DEFAULT,
                    cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
                    onScale = {},
                    openUrl = {},
                    onPreviewWebInfo = {},
                    progressWheelState = false,
                    onViewRecords = {},
                    onAbort = {},
                    onGettingApplicationStarted = {},
                    produceResult = produceFakeResult,
                    updateResult = {},
                    initializeApp = {},
                    result = HighestProbabilityComponent.Default,
                    minimizeBottomSheet = {},
                    expandBottomSheet = {},
                    onBufferResult = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(com.nkoyo.componentidentifier.R.string.getting_started_dialog)
        ).assertIsDisplayed()
    }


    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun gettingStartedDialog_whenAppIsStarting_hasText() {
        composeTestRule.setContent {
            BoxWithConstraints {
                MainScreen(
                    emptyImageUri = Uri.parse(EMPTY_FILE),
                    imageUri = Uri.parse(EMPTY_FILE),
                    gettingStartedState = true,
                    rotationAngle = 0f,
                    clearSavedUri = {},
                    windowSizeClass = calculateWindowSizeClass(activity = composeTestRule.activity),
                    flashLightState = ImageCaptureFlashMode.Off,
                    onTakeSnapshot = {},
                    onToggleFlashLight = {},
                    onToggleCamera = {},
                    bottomSheetMinimized = false,
                    classificationState = true,
                    cameraExecutorService = Executors.newSingleThreadExecutor(),
                    flashlightExecutorService = Executors.newSingleThreadExecutor(),
                    info = ComponentInfo.DEFAULT,
                    cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
                    onScale = {},
                    openUrl = {},
                    onPreviewWebInfo = {},
                    progressWheelState = false,
                    onViewRecords = {},
                    onAbort = {},
                    onGettingApplicationStarted = {},
                    produceResult = produceFakeResult,
                    updateResult = {},
                    initializeApp = {},
                    result = HighestProbabilityComponent.Default,
                    minimizeBottomSheet = {},
                    expandBottomSheet = {},
                    onBufferResult = {}
                )
            }
        }

        composeTestRule.onNode(
            hasText(
                composeTestRule.activity.resources.getString(
                    com.nkoyo.componentidentifier.R.string.developer_name
                )
            )
        ).assertIsDisplayed()

        composeTestRule.onNode(
            hasText(
                composeTestRule.activity.resources.getString(
                    com.nkoyo.componentidentifier.R.string.mat_no
                )
            )
        ).assertIsDisplayed()

        composeTestRule.onNode(
            hasText(
                composeTestRule.activity.resources.getString(
                    com.nkoyo.componentidentifier.R.string.developer_location
                )
            )
        ).assertIsDisplayed()

        val valueMessage = buildAnnotatedString {
            append(
                "Now you can identify your electrical and electronic components using "
            )
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(
                    composeTestRule.activity.resources.getString(
                        com.nkoyo.componentidentifier.R.string.app_name
                    )
                )
            }
            append(". This uses machine learning to produce component information with a high accuracy.")
        }

        composeTestRule.onNode(hasText(valueMessage.toString())).assertIsDisplayed()
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun gettingStartedDialog_whenAppIsStarting_hasButtons() {
        composeTestRule.setContent {
            BoxWithConstraints {
                MainScreen(
                    emptyImageUri = Uri.parse(EMPTY_FILE),
                    imageUri = Uri.parse(EMPTY_FILE),
                    gettingStartedState = true,
                    rotationAngle = 0f,
                    clearSavedUri = {},
                    windowSizeClass = calculateWindowSizeClass(activity = composeTestRule.activity),
                    flashLightState = ImageCaptureFlashMode.Off,
                    onTakeSnapshot = {},
                    onToggleFlashLight = {},
                    onToggleCamera = {},
                    bottomSheetMinimized = false,
                    classificationState = true,
                    cameraExecutorService = Executors.newSingleThreadExecutor(),
                    flashlightExecutorService = Executors.newSingleThreadExecutor(),
                    info = ComponentInfo.DEFAULT,
                    cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
                    onScale = {},
                    openUrl = {},
                    onPreviewWebInfo = {},
                    progressWheelState = false,
                    onViewRecords = {},
                    onAbort = {},
                    onGettingApplicationStarted = {},
                    produceResult = produceFakeResult,
                    updateResult = {},
                    initializeApp = {},
                    result = HighestProbabilityComponent.Default,
                    minimizeBottomSheet = {},
                    expandBottomSheet = {},
                    onBufferResult = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.abort_button
            )
        ).assertIsDisplayed()


        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.get_started_button
            )
        ).assertIsDisplayed()
    }


    @Test
    fun gettingStartedDialog_whenAbortButtonIsClicked_appShouldClose() {
        composeTestRule.setContent {
            BoxWithConstraints {
                MainScreen(
                    emptyImageUri = Uri.parse(EMPTY_FILE),
                    imageUri = Uri.parse(EMPTY_FILE),
                    gettingStartedState = true,
                    rotationAngle = 0f,
                    clearSavedUri = {},
                    windowSizeClass = calculateWindowSizeClass(activity = composeTestRule.activity),
                    flashLightState = ImageCaptureFlashMode.Off,
                    onTakeSnapshot = {},
                    onToggleFlashLight = {},
                    onToggleCamera = {},
                    bottomSheetMinimized = false,
                    classificationState = true,
                    cameraExecutorService = Executors.newSingleThreadExecutor(),
                    flashlightExecutorService = Executors.newSingleThreadExecutor(),
                    info = ComponentInfo.DEFAULT,
                    cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
                    onScale = {},
                    openUrl = {},
                    onPreviewWebInfo = {},
                    progressWheelState = false,
                    onViewRecords = {},
                    onAbort = {
                        composeTestRule.activity.finish()
                    },
                    onGettingApplicationStarted = {},
                    produceResult = produceFakeResult,
                    updateResult = {},
                    initializeApp = {},
                    result = HighestProbabilityComponent.Default,
                    minimizeBottomSheet = {},
                    expandBottomSheet = {},
                    onBufferResult = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.abort_button
            )
        ).assertHasClickAction()


        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.abort_button
            )
        ).performClick()
    }


    @Test
    fun mainPreview_whenGettingStartedButtonClicked_shouldDisplay() {
        composeTestRule.setContent {
            var gettingStartedState by remember { mutableStateOf(true) }

            BoxWithConstraints {
                MainScreen(
                    emptyImageUri = Uri.parse(EMPTY_FILE),
                    imageUri = Uri.parse(EMPTY_FILE),
                    gettingStartedState = gettingStartedState,
                    rotationAngle = 0f,
                    clearSavedUri = {},
                    windowSizeClass = calculateWindowSizeClass(activity = composeTestRule.activity),
                    flashLightState = ImageCaptureFlashMode.Off,
                    onTakeSnapshot = {},
                    onToggleFlashLight = {},
                    onToggleCamera = {},
                    bottomSheetMinimized = false,
                    classificationState = true,
                    cameraExecutorService = Executors.newSingleThreadExecutor(),
                    flashlightExecutorService = Executors.newSingleThreadExecutor(),
                    info = ComponentInfo.DEFAULT,
                    cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
                    onScale = {},
                    openUrl = {},
                    onPreviewWebInfo = {},
                    progressWheelState = false,
                    onViewRecords = {},
                    onAbort = {},
                    onGettingApplicationStarted = {
                        gettingStartedState = false
                    },
                    produceResult = produceFakeResult,
                    updateResult = {},
                    initializeApp = {},
                    result = HighestProbabilityComponent.Default,
                    minimizeBottomSheet = {},
                    expandBottomSheet = {},
                    onBufferResult = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.get_started_button
            )
        ).assertHasClickAction()


        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.get_started_button
            )
        ).performClick()

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.close_button
            )
        ).assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.flashlight_button_off
            )
        ).assertIsDisplayed()


        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.show_record_button
            )
        ).assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.snapshot_button
            )
        ).assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.camera_flip_button
            )
        ).assertIsDisplayed()

    }

    @Test
    fun mainPreview_whenOnCreated_showProgressIndicator() {
        composeTestRule.setContent {
            var gettingStartedState by remember { mutableStateOf(true) }
            var bottomSheetMinimized by remember { mutableStateOf(true) }
            var classificationState by remember { mutableStateOf(true) }
            val progressWheelState =
                !gettingStartedState && bottomSheetMinimized && classificationState


            BoxWithConstraints {
                MainScreen(
                    emptyImageUri = Uri.parse(EMPTY_FILE),
                    imageUri = Uri.parse(EMPTY_FILE),
                    gettingStartedState = gettingStartedState,
                    rotationAngle = 0f,
                    clearSavedUri = {},
                    windowSizeClass = calculateWindowSizeClass(activity = composeTestRule.activity),
                    flashLightState = ImageCaptureFlashMode.Off,
                    onTakeSnapshot = {},
                    onToggleFlashLight = {},
                    onToggleCamera = {},
                    bottomSheetMinimized = bottomSheetMinimized,
                    classificationState = classificationState,
                    cameraExecutorService = Executors.newSingleThreadExecutor(),
                    flashlightExecutorService = Executors.newSingleThreadExecutor(),
                    info = ComponentInfo.DEFAULT,
                    cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
                    onScale = {
                        bottomSheetMinimized = !bottomSheetMinimized
                    },
                    openUrl = {},
                    onPreviewWebInfo = {},
                    progressWheelState = progressWheelState,
                    onViewRecords = {},
                    onAbort = {},
                    onGettingApplicationStarted = {
                        gettingStartedState = false
                    },
                    produceResult = produceFakeResult,
                    updateResult = {},
                    initializeApp = {
                        bottomSheetMinimized = true
                        classificationState = true
                    },
                    result = HighestProbabilityComponent.Default,
                    minimizeBottomSheet = {},
                    expandBottomSheet = {},
                    onBufferResult = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.get_started_button
            )
        ).performClick()

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.circular_progress_indicator
            )
        ).assertExists()
    }


    @Test
    fun flashlightButton_whenToggled_changesVisibility() {
        composeTestRule.setContent {
            var gettingStartedState by remember { mutableStateOf(true) }
            var bottomSheetMinimized by remember { mutableStateOf(true) }
            var classificationState by remember { mutableStateOf(true) }
            val progressWheelState =
                !gettingStartedState && bottomSheetMinimized && classificationState
            var flashlightState: ImageCaptureFlashMode by remember {
                mutableStateOf(
                    ImageCaptureFlashMode.Off
                )
            }


            BoxWithConstraints {
                MainScreen(
                    emptyImageUri = Uri.parse(EMPTY_FILE),
                    imageUri = Uri.parse(EMPTY_FILE),
                    gettingStartedState = gettingStartedState,
                    rotationAngle = 0f,
                    clearSavedUri = {},
                    windowSizeClass = calculateWindowSizeClass(activity = composeTestRule.activity),
                    flashLightState = flashlightState,
                    onTakeSnapshot = {},
                    onToggleFlashLight = {
                        flashlightState = when (flashlightState) {
                            ImageCaptureFlashMode.Off -> {
                                ImageCaptureFlashMode.Auto
                            }

                            ImageCaptureFlashMode.On -> {
                                ImageCaptureFlashMode.Off
                            }

                            ImageCaptureFlashMode.Auto -> {
                                ImageCaptureFlashMode.On
                            }
                        }
                    },
                    onToggleCamera = {},
                    bottomSheetMinimized = bottomSheetMinimized,
                    classificationState = classificationState,
                    cameraExecutorService = Executors.newSingleThreadExecutor(),
                    flashlightExecutorService = Executors.newSingleThreadExecutor(),
                    info = ComponentInfo.DEFAULT,
                    cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
                    onScale = {
                        bottomSheetMinimized = !bottomSheetMinimized
                    },
                    openUrl = {},
                    onPreviewWebInfo = {},
                    progressWheelState = progressWheelState,
                    onViewRecords = {},
                    onAbort = {},
                    onGettingApplicationStarted = {
                        gettingStartedState = false
                    },
                    produceResult = produceFakeResult,
                    updateResult = {},
                    initializeApp = {
                        bottomSheetMinimized = true
                        classificationState = true
                    },
                    result = HighestProbabilityComponent.Default,
                    minimizeBottomSheet = {},
                    expandBottomSheet = {},
                    onBufferResult = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.get_started_button
            )
        ).performClick()

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.flashlight_button_off
            )
        ).performClick()

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.flashlight_button_on
            )
        ).performClick()

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.flashlight_button_on
            )
        ).performClick()

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.flashlight_button_off
            )
        ).performClick()
    }

    @Test
    fun bottomSheetForCompact_whenProgressBarIsShown_shouldDisplay() {
        composeTestRule.setContent {
            var gettingStartedState by remember { mutableStateOf(true) }
            var bottomSheetMinimized by remember { mutableStateOf(true) }
            var classificationState by remember { mutableStateOf(true) }
            val progressWheelState =
                !gettingStartedState && bottomSheetMinimized && classificationState
            var flashlightState: ImageCaptureFlashMode by remember {
                mutableStateOf(
                    ImageCaptureFlashMode.Off
                )
            }


            BoxWithConstraints {
                MainScreen(
                    emptyImageUri = Uri.parse(EMPTY_FILE),
                    imageUri = Uri.parse(EMPTY_FILE),
                    gettingStartedState = gettingStartedState,
                    rotationAngle = 0f,
                    clearSavedUri = {},
                    windowSizeClass = calculateWindowSizeClass(activity = composeTestRule.activity),
                    flashLightState = flashlightState,
                    onTakeSnapshot = {},
                    onToggleFlashLight = {
                        flashlightState = when (flashlightState) {
                            ImageCaptureFlashMode.Off -> {
                                ImageCaptureFlashMode.Auto
                            }

                            ImageCaptureFlashMode.On -> {
                                ImageCaptureFlashMode.Off
                            }

                            ImageCaptureFlashMode.Auto -> {
                                ImageCaptureFlashMode.On
                            }
                        }
                    },
                    onToggleCamera = {},
                    bottomSheetMinimized = bottomSheetMinimized,
                    classificationState = classificationState,
                    cameraExecutorService = Executors.newSingleThreadExecutor(),
                    flashlightExecutorService = Executors.newSingleThreadExecutor(),
                    info = ComponentInfo.DEFAULT,
                    cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
                    onScale = {
                        bottomSheetMinimized = !bottomSheetMinimized
                    },
                    openUrl = {},
                    onPreviewWebInfo = {},
                    progressWheelState = progressWheelState,
                    onViewRecords = {},
                    onAbort = {},
                    onGettingApplicationStarted = {
                        gettingStartedState = false
                    },
                    produceResult = produceFakeResult,
                    updateResult = {},
                    initializeApp = {
                        bottomSheetMinimized = true
                        classificationState = true
                    },
                    result = HighestProbabilityComponent.Default,
                    minimizeBottomSheet = {},
                    expandBottomSheet = {},
                    onBufferResult = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.get_started_button
            )
        ).performClick()

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.bottom_sheet_compact
            )
        ).assertExists()

    }

    @Test
    fun bottomSheetForCompat_whenScaleButtonClicked_shouldChangeSize() {
        composeTestRule.setContent {
            var gettingStartedState by remember { mutableStateOf(true) }
            var bottomSheetMinimized by remember { mutableStateOf(true) }
            var classificationState by remember { mutableStateOf(true) }
            val progressWheelState =
                !gettingStartedState && bottomSheetMinimized && classificationState
            var flashlightState: ImageCaptureFlashMode by remember {
                mutableStateOf(
                    ImageCaptureFlashMode.Off
                )
            }


            BoxWithConstraints {
                MainScreen(
                    emptyImageUri = Uri.parse(EMPTY_FILE),
                    imageUri = Uri.parse(EMPTY_FILE),
                    gettingStartedState = gettingStartedState,
                    rotationAngle = 0f,
                    clearSavedUri = {},
                    windowSizeClass = calculateWindowSizeClass(activity = composeTestRule.activity),
                    flashLightState = flashlightState,
                    onTakeSnapshot = {},
                    onToggleFlashLight = {
                        flashlightState = when (flashlightState) {
                            ImageCaptureFlashMode.Off -> {
                                ImageCaptureFlashMode.Auto
                            }

                            ImageCaptureFlashMode.On -> {
                                ImageCaptureFlashMode.Off
                            }

                            ImageCaptureFlashMode.Auto -> {
                                ImageCaptureFlashMode.On
                            }
                        }
                    },
                    onToggleCamera = {},
                    bottomSheetMinimized = bottomSheetMinimized,
                    classificationState = classificationState,
                    cameraExecutorService = Executors.newSingleThreadExecutor(),
                    flashlightExecutorService = Executors.newSingleThreadExecutor(),
                    info = ComponentInfo.DEFAULT,
                    cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
                    onScale = {
                        bottomSheetMinimized = !bottomSheetMinimized
                    },
                    openUrl = {},
                    onPreviewWebInfo = {},
                    progressWheelState = progressWheelState,
                    onViewRecords = {},
                    onAbort = {},
                    onGettingApplicationStarted = {
                        gettingStartedState = false
                    },
                    produceResult = produceFakeResult,
                    updateResult = {},
                    initializeApp = {
                        bottomSheetMinimized = true
                        classificationState = true
                    },
                    result = HighestProbabilityComponent.Default,
                    minimizeBottomSheet = {},
                    expandBottomSheet = {},
                    onBufferResult = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.get_started_button
            )
        ).performClick()

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.bottom_sheet_compact
            )
        ).assertExists()

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.maximize_button
            )
        ).performClick()

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.minimize_button
            )
        ).performClick()

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.maximize_button
            )
        ).performClick()

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.minimize_button
            )
        ).performClick()

    }


    @OptIn(ExperimentalTestApi::class)
    @Test
    fun progressWheelIndicator_whenBottomSheetMinimized_shouldNotDisplay() {
        composeTestRule.setContent {
            var gettingStartedState by remember { mutableStateOf(true) }
            var bottomSheetMinimized by remember { mutableStateOf(true) }
            var classificationState by remember { mutableStateOf(true) }
            val progressWheelState =
                !gettingStartedState && bottomSheetMinimized && classificationState
            var flashlightState: ImageCaptureFlashMode by remember {
                mutableStateOf(
                    ImageCaptureFlashMode.Off
                )
            }


            LaunchedEffect(
                gettingStartedState,
                classificationState,
                bottomSheetMinimized
            ) {
                if (!classificationState) bottomSheetMinimized = true
                if (gettingStartedState) return@LaunchedEffect
                delay(3_000)
                if (classificationState && bottomSheetMinimized) {
                    bottomSheetMinimized = false
                }
            }

            BoxWithConstraints {
                MainScreen(
                    emptyImageUri = Uri.parse(EMPTY_FILE),
                    imageUri = Uri.parse(EMPTY_FILE),
                    gettingStartedState = gettingStartedState,
                    rotationAngle = 0f,
                    clearSavedUri = {},
                    windowSizeClass = calculateWindowSizeClass(activity = composeTestRule.activity),
                    flashLightState = flashlightState,
                    onTakeSnapshot = {
                    },
                    onToggleFlashLight = {
                        flashlightState = when (flashlightState) {
                            ImageCaptureFlashMode.Off -> {
                                ImageCaptureFlashMode.Auto
                            }

                            ImageCaptureFlashMode.On -> {
                                ImageCaptureFlashMode.Off
                            }

                            ImageCaptureFlashMode.Auto -> {
                                ImageCaptureFlashMode.On
                            }
                        }
                    },
                    onToggleCamera = {},
                    bottomSheetMinimized = bottomSheetMinimized,
                    classificationState = classificationState,
                    cameraExecutorService = Executors.newSingleThreadExecutor(),
                    flashlightExecutorService = Executors.newSingleThreadExecutor(),
                    info = ComponentInfo.DEFAULT,
                    cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
                    onScale = {
                        classificationState = !classificationState
                    },
                    openUrl = {},
                    onPreviewWebInfo = {},
                    progressWheelState = progressWheelState,
                    onViewRecords = {},
                    onAbort = {
                    },
                    onGettingApplicationStarted = {
                        gettingStartedState = false
                    },
                    produceResult = produceFakeResult,
                    updateResult = {},
                    initializeApp = {
                        bottomSheetMinimized = true
                        classificationState = true
                    },
                    result = HighestProbabilityComponent.Default,
                    minimizeBottomSheet = { bottomSheetMinimized = true },
                    expandBottomSheet = { bottomSheetMinimized = false },
                    onBufferResult = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.get_started_button
            )
        ).performClick()

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.bottom_sheet_compact
            )
        ).assertExists()

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.circular_progress_indicator
            )
        ).assertIsDisplayed()


        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.maximize_button
            )
        ).performClick()

        composeTestRule.waitUntilDoesNotExist(
            hasContentDescription(
                composeTestRule.activity.resources.getString(
                    com.nkoyo.componentidentifier.R.string.circular_progress_indicator
                )
            ), 5_000
        )


        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.bottom_sheet_compact
            )
        ).assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.circular_progress_indicator
            )
        ).assertDoesNotExist()

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(
                com.nkoyo.componentidentifier.R.string.minimize_button
            )
        ).performClick()
    }

}