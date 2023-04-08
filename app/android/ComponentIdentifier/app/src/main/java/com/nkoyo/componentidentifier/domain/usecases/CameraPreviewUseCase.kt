package com.nkoyo.componentidentifier.domain.usecases

import androidx.camera.core.Preview
import androidx.camera.view.PreviewView

class CameraPreviewUseCase{
    fun of(view: PreviewView) = Preview.Builder()
        .build()
        .also { it.setSurfaceProvider(view.surfaceProvider) }
}
