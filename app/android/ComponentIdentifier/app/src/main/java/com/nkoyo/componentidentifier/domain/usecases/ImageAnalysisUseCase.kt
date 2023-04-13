package com.nkoyo.componentidentifier.domain.usecases

import android.util.Size
import androidx.camera.core.ImageAnalysis


class ImageAnalysisUseCase {
    fun of(size: Size = Size(1280, 720)): ImageAnalysis {
        val builder = ImageAnalysis.Builder()
            .setTargetResolution(size)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        return builder.build()
    }
}