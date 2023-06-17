package com.nkoyo.componentidentifier.ui.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.nkoyo.componentidentifier.database.HistoryEntity
import com.nkoyo.componentidentifier.datastore.DarkThemeConfig
import com.nkoyo.componentidentifier.datastore.NkPreferenceDataSource
import com.nkoyo.componentidentifier.domain.classifier.ComponentClassifier
import com.nkoyo.componentidentifier.domain.repository.HistoryRepository
import com.nkoyo.componentidentifier.domain.usecases.ImageCaptureFlashMode
import com.nkoyo.componentidentifier.ui.components.DarkThemeConfigSettings
import com.nkoyo.componentidentifier.ui.components.TestRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

const val KEY_SELECTED_HISTORY_ID = "selected_history_id"
const val KEY_SELECTED_URL = "selected_url"
const val EMPTY_FILE = "file://dev/null"

@HiltViewModel
class MainViewModel @Inject constructor(
    @SuppressLint("StaticFieldLeak") @ApplicationContext private val context: Context,
    private val componentClassifier: ComponentClassifier,
    private val historyRepository: HistoryRepository,
    private val savedStateHandle: SavedStateHandle,
    private val preferences: NkPreferenceDataSource,
) : ViewModel() {
    val TAG = "MainViewModel"

    init {
        initialize() //initialize Classifier
    }

    private val _cameraSelector =
        MutableStateFlow<CameraSelector>(CameraSelector.DEFAULT_BACK_CAMERA)
    val cameraSelector: StateFlow<CameraSelector> = _cameraSelector

    private var _flashLightState =
        MutableStateFlow(ImageCaptureFlashMode.Auto as ImageCaptureFlashMode)
    val flashLightState: StateFlow<ImageCaptureFlashMode> = _flashLightState

    val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    val flashLightExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    private var _bottomSheetMinimized = MutableStateFlow(true)
    val bottomSheetMinimized: StateFlow<Boolean> = _bottomSheetMinimized

    private var _classificationState = MutableStateFlow(true)
    val classificationState: StateFlow<Boolean> = _classificationState

    private var _menuState = MutableStateFlow(false)
    val menuState: StateFlow<Boolean> = _menuState

    private var _darkModeDialogState = MutableStateFlow(false)
    val darkModeDialogState: StateFlow<Boolean> = _darkModeDialogState

    private val _savedImageUri = MutableStateFlow<Uri>(Uri.parse(EMPTY_FILE))
    val savedImageUri: StateFlow<Uri> = _savedImageUri

    val darkThemeConfigSettings: StateFlow<DarkThemeConfigSettings> = preferences.userData.map {
        DarkThemeConfigSettings(it.darkThemeConfig)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DarkThemeConfigSettings(DarkThemeConfig.FOLLOW_SYSTEM)
    )

    private val _gettingStartedState = MutableStateFlow<Boolean>(true)
    val gettingStartedState: StateFlow<Boolean> = _gettingStartedState


    val progressWheelState: StateFlow<Boolean> = combine(
        _bottomSheetMinimized,
        _gettingStartedState,
        _classificationState
    ) { bottomSheet, gettingStarted, classification ->
        classification && bottomSheet && !gettingStarted
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false,
    )


    private val _result =
        MutableStateFlow<HighestProbabilityComponent>(HighestProbabilityComponent.Default)
    val result: StateFlow<HighestProbabilityComponent> =
        _result

    private val _resultBuffer =
        MutableStateFlow<HighestProbabilityComponent>(HighestProbabilityComponent.Default)
    val resultBuffer: StateFlow<HighestProbabilityComponent> =
        _resultBuffer

    val historyAsPaged: Flow<PagingData<HistoryEntity>> =
        historyRepository.showHistoryAsPaged(20)

    val selectedHistoryId: StateFlow<Long> = savedStateHandle.getStateFlow(
        key = KEY_SELECTED_HISTORY_ID, initialValue = -1L,
    )

    private val _selectedHistoryEntity = MutableStateFlow<HistoryEntity?>(null)
    val selectedHistoryEntity: StateFlow<HistoryEntity?> = _selectedHistoryEntity

    val selectedUrl: StateFlow<String> = savedStateHandle.getStateFlow(
        key = KEY_SELECTED_URL, initialValue = ""
    )

    fun updateSavedUri(uri: Uri) {
        _savedImageUri.value = uri
    }

    fun clearSavedUri() {
        _savedImageUri.value = Uri.parse(EMPTY_FILE)
    }

    private val _highestProbabilityComponentLabel = MutableStateFlow<String>("")
    val highestProbabilityComponentLabel: StateFlow<String> = _highestProbabilityComponentLabel

    fun navigateToHistoryDetails(newId: Long) {
        savedStateHandle[KEY_SELECTED_HISTORY_ID] = newId
    }

    fun openWebUrl(url: String) {
        savedStateHandle[KEY_SELECTED_URL] = url
    }

    fun updateResult(component: HighestProbabilityComponent) {
        _result.value = component
    }

    fun onBottomSheetMinimizedChanged(minimized: Boolean) {
        _bottomSheetMinimized.value = minimized
    }

    fun updateClassificationState(state: Boolean) {
        _classificationState.value = state
    }

    fun onToggleCameraSelector(selector: CameraSelector) {
        when (selector) {
            CameraSelector.DEFAULT_BACK_CAMERA -> _cameraSelector.value =
                CameraSelector.DEFAULT_FRONT_CAMERA

            CameraSelector.DEFAULT_FRONT_CAMERA -> _cameraSelector.value =
                CameraSelector.DEFAULT_BACK_CAMERA
        }
    }

    fun updateResultLabel(label: String) {
        _highestProbabilityComponentLabel.value = label
    }

    fun onBufferResult(result: HighestProbabilityComponent) {
        _resultBuffer.value = result
    }

    fun onFlashLightStateChanged(state: ImageCaptureFlashMode) {
        when (state) {
            //  circular control
            is ImageCaptureFlashMode.Auto -> {
                _flashLightState.value = ImageCaptureFlashMode.On
            }

            is ImageCaptureFlashMode.On -> {
                _flashLightState.value = ImageCaptureFlashMode.Off
            }

            is ImageCaptureFlashMode.Off -> {
                _flashLightState.value = ImageCaptureFlashMode.Auto
            }
        }
    }

    fun classify(bitmap: Bitmap): HashMap<String, String> {
        return componentClassifier.classify(bitmap)
    }

    fun classifyAndProduceHighestProbabilityLabel(bitmap: Bitmap): HighestProbabilityComponent {
        val (label, probability) = componentClassifier.classifyAndProduceHighestProbabilityLabel(
            bitmap
        )
        return HighestProbabilityComponent(label, probability)
    }

    fun loadLabelData(filename: String): ArrayList<String> {
        return componentClassifier.loadLabelData(context, filename)
    }

    private fun initialize() = componentClassifier.initialize()

    fun close() = componentClassifier.close()


    fun onApplicationStarted() {
        _gettingStartedState.value = false
    }

    fun registerHistory(componentName: String, imageUrl: String) {
        viewModelScope.launch {
            historyRepository.registerHistory(
                HistoryEntity(
                    historyId = System.currentTimeMillis(),
                    componentName = componentName,
                    imageUrl = imageUrl,
                    dateTime = LocalDateTime.now()
                )
            )
        }
    }

    fun deleteHistory(historyEntity: HistoryEntity) {
        viewModelScope.launch {
            historyRepository.deleteHistory(historyEntity)
        }
    }

    fun clearHistory() {
        viewModelScope.launch { historyRepository.clearHistory() }
    }

    private fun updateSelectedHistoryEntity(history: HistoryEntity?) {
        _selectedHistoryEntity.value = history
    }

    fun findByComponentId(id: Long) {
        viewModelScope.launch {
            val historyEntity = historyRepository.findByComponentId(id)
            historyEntity.collectLatest {
                updateSelectedHistoryEntity(it)
                Log.e(TAG, "findByComponentId: selected historyitem is $it")
            }
        }
    }

    fun updateMenuState(state: Boolean) {
        _menuState.value = state
    }

    fun updateDarkModeDialogState(state: Boolean) {
        _darkModeDialogState.value = state
    }

    fun updateDarkThemeConfigState(state: DarkThemeConfig) {
        viewModelScope.launch { preferences.updateDarkThemeConfig(state) }
    }
}


data class HighestProbabilityComponent(
    val label: String,
    val prob: Float,
) {
    companion object {
        val Default = HighestProbabilityComponent(
            label = "",
            prob = 0f,
        )
    }
}
