package com.nkoyo.componentidentifier.ui.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.media.Image
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.ObjectDetector
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions
import com.nkoyo.componentidentifier.data.classifier.MODEL_FILENAME_TEST
import com.nkoyo.componentidentifier.database.HistoryDao
import com.nkoyo.componentidentifier.database.HistoryEntity
import com.nkoyo.componentidentifier.domain.classifier.ComponentClassifier
import com.nkoyo.componentidentifier.domain.repository.HistoryRepository
import com.nkoyo.componentidentifier.domain.usecases.ImageCaptureFlashMode
import com.nkoyo.componentidentifier.ui.components.TestRecord
import com.nkoyo.componentidentifier.ui.screens.history.HistoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

const val KEY_SELECTED_HISTORY_ID = "selected_history_id"
const val KEY_SELECTED_URL = "selected_url"

@HiltViewModel
class MainViewModel @Inject constructor(
    @SuppressLint("StaticFieldLeak") @ApplicationContext private val context: Context,
    private val componentClassifier: ComponentClassifier,
    private val historyRepository: HistoryRepository,
    private val savedStateHandle: SavedStateHandle,
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

    private var _testRecords = MutableStateFlow(mutableListOf<TestRecord>())
    val testRecords: StateFlow<List<TestRecord>> = _testRecords

    private var _bottomSheetMinimized = MutableStateFlow(true)
    val bottomSheetMinimized: StateFlow<Boolean> = _bottomSheetMinimized

    private val _gettingStartedState = MutableStateFlow<Boolean>(true)
    val gettingStartedState: StateFlow<Boolean> = _gettingStartedState


    private val _currentHighestProbabilityComponent =
        MutableStateFlow<HighestProbabilityComponent>(HighestProbabilityComponent.Default)
    val currentHighestProbabilityComponent: StateFlow<HighestProbabilityComponent> =
        _currentHighestProbabilityComponent

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

    private val _highestProbabilityComponentLabel = MutableStateFlow<String>("")
    val highestProbabilityComponentLabel: StateFlow<String> = _highestProbabilityComponentLabel

    fun navigateToHistoryDetails(newId: Long) {
        savedStateHandle[KEY_SELECTED_HISTORY_ID] = newId
    }

    fun openWebUrl(url: String) {
        savedStateHandle[KEY_SELECTED_URL] = url
    }

    fun updateHighestProbabilityComponent(component: HighestProbabilityComponent) {
        _currentHighestProbabilityComponent.value = component
    }

    fun onBottomSheetMinimizedChanged(minimized: Boolean) {
        _bottomSheetMinimized.value = minimized
    }

    fun onTestRecordsChanged(records: List<TestRecord>) {
        _testRecords.value = records.toMutableList()
        Log.e(TAG, "onTestRecordsChanged: testRecords size is ${_testRecords.value.size}")
    }

    fun onToggleCameraSelector(selector: CameraSelector) {
        when (selector) {
            CameraSelector.DEFAULT_BACK_CAMERA -> _cameraSelector.value =
                CameraSelector.DEFAULT_FRONT_CAMERA

            CameraSelector.DEFAULT_FRONT_CAMERA -> _cameraSelector.value =
                CameraSelector.DEFAULT_BACK_CAMERA
        }
    }

    fun updateHighestProbabilityLabel(label: String) {
        _highestProbabilityComponentLabel.value = label
    }

    fun onFlashLightStateChanged(state: ImageCaptureFlashMode) {
        _flashLightState.value = state
    }

    fun classify(bitmap: Bitmap): HashMap<String, String> {
        return componentClassifier.classify(bitmap)
    }

    fun classifyAndProduceHighestProbabilityLabel(bitmap: Bitmap): Pair<String, Float> {
        return componentClassifier.classifyAndProduceHighestProbabilityLabel(bitmap)
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
