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
import androidx.lifecycle.ViewModel
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.ObjectDetector
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions
import com.nkoyo.componentidentifier.data.classifier.MODEL_FILENAME_TEST
import com.nkoyo.componentidentifier.domain.classifier.ComponentClassifier
import com.nkoyo.componentidentifier.domain.usecases.ImageCaptureFlashMode
import com.nkoyo.componentidentifier.ui.components.TestRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.ByteArrayOutputStream
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

    val cameraSelector: CameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

    private var _flashLightState =
        MutableStateFlow(ImageCaptureFlashMode.Auto as ImageCaptureFlashMode)
    val flashLightState: StateFlow<ImageCaptureFlashMode> = _flashLightState

    val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    val flashLightExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    private var _testRecords = MutableStateFlow(mutableListOf<TestRecord>())
    val testRecords: StateFlow<List<TestRecord>> = _testRecords

    private var _bottomSheetMinimized = MutableStateFlow(false)
    val bottomSheetMinimized: StateFlow<Boolean> = _bottomSheetMinimized

    private val _gettingStartedState = MutableStateFlow<Boolean>(true)
    val gettingStartedState: StateFlow<Boolean> = _gettingStartedState


    private val _objectBoundingBoxes = MutableStateFlow<ArrayList<ObjectBoundingBox>>(arrayListOf())
    val objectBoundingBoxes: StateFlow<ArrayList<ObjectBoundingBox>> = _objectBoundingBoxes

    fun onBottomSheetMinimizedChanged(minimized: Boolean) {
        _bottomSheetMinimized.value = minimized
    }

    fun onTestRecordsChanged(records: List<TestRecord>) {
        _testRecords.value = records.toMutableList()
        Log.e(TAG, "onTestRecordsChanged: testRecords size is ${_testRecords.value.size}")
    }

    private fun onCameraSelectorChanged(selector: CameraSelector) {
        // _cameraSelector.value = selector
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

    private fun initialize() = componentClassifier.initialize()

    fun runObjectDetection(
        bitmap: Bitmap?,
        rotationDegrees: Int,
        onSuccess: ObjectDetectionSuccessListener,
        onFailure: ObjectDetectionFailureListener,
    ) {
        val model = "obj_detection_sample_1.tflite"
        //val model = "object_detection_mobile_google.tflite"
        if (bitmap == null) return
        val localModel = LocalModel.Builder()
            .setAssetFilePath(MODEL_FILENAME_TEST).build()
        val options = CustomObjectDetectorOptions.Builder(localModel)
            .setDetectorMode(CustomObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .enableMultipleObjects()
            .enableClassification()
            .build()
        val detector = ObjectDetection.getClient(options)
        val inputImage = InputImage.fromBitmap(bitmap, rotationDegrees)
        detector.process(inputImage).addOnSuccessListener(onSuccess)
            .addOnFailureListener(onFailure)
    }

    fun close() = componentClassifier.close()


    fun onApplicationStarted() {
        _gettingStartedState.value = false
    }

    fun addToObjectBoundingBoxes(objectBoundingBox: ObjectBoundingBox) {
        if (_objectBoundingBoxes.value.find { it.box == objectBoundingBox.box || it.text == objectBoundingBox.text } != null)
            _objectBoundingBoxes.value.remove(objectBoundingBox)
        _objectBoundingBoxes.value.add(objectBoundingBox)
    }

    fun removeFromObjectBoundingBoxes(objectBoundingBox: ObjectBoundingBox) {
        _objectBoundingBoxes.value.remove(objectBoundingBox)
    }

    fun clearObjectBoundingBoxes() {
        _objectBoundingBoxes.value.clear()
    }
}

data class ObjectBoundingBox(
    val text: String,
    val box: Rect,
)

typealias ObjectDetectionSuccessListener = (List<DetectedObject>) -> Unit
typealias ObjectDetectionFailureListener = (Exception) -> Unit
