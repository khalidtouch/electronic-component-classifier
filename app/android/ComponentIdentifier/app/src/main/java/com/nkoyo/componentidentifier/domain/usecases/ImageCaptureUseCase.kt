package com.nkoyo.componentidentifier.domain.usecases

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY

class ImageCaptureUseCase {
    fun of()  = ImageCapture.Builder()
        .setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY)
        .build()
}