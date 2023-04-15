package com.nkoyo.componentidentifier.ui.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import androidx.lifecycle.ViewModel
import com.nkoyo.componentidentifier.domain.classifier.ComponentClassifier
import com.nkoyo.componentidentifier.domain.usecases.ImageCaptureFlashMode
import com.nkoyo.componentidentifier.ui.components.TestRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val componentClassifier: ComponentClassifier
) : ViewModel() {
    val TAG = "MainViewModel"
    init {
       initialize() //initialize Classifier
    }

    private var _cameraSelector = MutableStateFlow(CameraSelector.DEFAULT_BACK_CAMERA)
    val cameraSelector: StateFlow<CameraSelector> = _cameraSelector

    private var _flashLightState =
        MutableStateFlow(ImageCaptureFlashMode.Auto as ImageCaptureFlashMode)
    val flashLightState: StateFlow<ImageCaptureFlashMode> = _flashLightState

    val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    val flashLightExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    private var _testRecords = MutableStateFlow(mutableListOf<TestRecord>())
    val testRecords: StateFlow<List<TestRecord>> = _testRecords

    private var _bottomSheetMinimized = MutableStateFlow(false)
    val bottomSheetMinimized: StateFlow<Boolean> = _bottomSheetMinimized

    fun onBottomSheetMinimizedChanged(minimized: Boolean) {
        _bottomSheetMinimized.value = minimized
    }

    fun onTestRecordsChanged(records: List<TestRecord>) {
        _testRecords.value.clear()
        _testRecords.value.addAll(records)
        Log.e(TAG, "onTestRecordsChanged: testRecords size is ${_testRecords.value.size}", )
    }

    fun onCameraSelectorChanged(selector: CameraSelector) {
        _cameraSelector.value = selector
    }

    fun onFlashLightStateChanged(state: ImageCaptureFlashMode) {
        _flashLightState.value = state
    }

    fun classify(bitmap: Bitmap): HashMap<String, String> {
        return componentClassifier.classify(bitmap)
    }

    fun loadLabelData(filename: String): ArrayList<String> {
        return componentClassifier.loadLabelData(context, filename)
    }

    fun initialize() = componentClassifier.initialize()

    fun close() = componentClassifier.close()


}