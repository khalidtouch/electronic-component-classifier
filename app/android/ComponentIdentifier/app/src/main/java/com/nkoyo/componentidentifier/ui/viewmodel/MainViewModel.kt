package com.nkoyo.componentidentifier.ui.viewmodel

import android.content.Context
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import androidx.lifecycle.ViewModel
import com.nkoyo.componentidentifier.domain.usecases.ImageCaptureFlashMode
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
   @ApplicationContext context: Context,
): ViewModel() {

    private var _cameraSelector = MutableStateFlow(CameraSelector.DEFAULT_BACK_CAMERA)
    val cameraSelector: StateFlow<CameraSelector> = _cameraSelector

    private var _flashLightState =
        MutableStateFlow(ImageCaptureFlashMode.Auto as ImageCaptureFlashMode)
    val flashLightState: StateFlow<ImageCaptureFlashMode> = _flashLightState

    fun onCameraSelectorChanged(selector: CameraSelector) {
        _cameraSelector.value = selector
    }

    fun onFlashLightStateChanged(state: ImageCaptureFlashMode) {
        _flashLightState.value = state
    }

}