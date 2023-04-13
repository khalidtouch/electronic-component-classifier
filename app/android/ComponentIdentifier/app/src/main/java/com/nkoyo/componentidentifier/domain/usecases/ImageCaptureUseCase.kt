package com.nkoyo.componentidentifier.domain.usecases

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.core.ImageCapture.FLASH_MODE_AUTO
import androidx.camera.core.ImageCapture.FLASH_MODE_OFF
import androidx.camera.core.ImageCapture.FLASH_MODE_ON

class ImageCaptureUseCase {
    fun of(
        flashMode: ImageCaptureFlashMode = ImageCaptureFlashMode.Auto
    ) = ImageCapture.Builder()
        .setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY)
        .setFlashMode(
            when (flashMode) {
                is ImageCaptureFlashMode.Auto -> FLASH_MODE_AUTO
                is ImageCaptureFlashMode.On -> FLASH_MODE_ON
                is ImageCaptureFlashMode.Off -> FLASH_MODE_OFF
            }
        )
        .build()
}


sealed class ImageCaptureFlashMode {
    object Auto : ImageCaptureFlashMode()
    object Off : ImageCaptureFlashMode()
    object On : ImageCaptureFlashMode()
}