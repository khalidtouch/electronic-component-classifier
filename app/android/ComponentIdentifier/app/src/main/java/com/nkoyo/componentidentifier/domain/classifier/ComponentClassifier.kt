package com.nkoyo.componentidentifier.domain.classifier

import android.content.Context
import android.graphics.Bitmap
import com.nkoyo.componentidentifier.ui.viewmodel.HighestProbabilityComponent

interface ComponentClassifier {
    fun initialize()

    fun classify(bitmap: Bitmap): HashMap<String, String>

    fun classifyAndProduceHighestProbabilityLabel(bitmap: Bitmap): Pair<String, Float>

    fun loadLabelData(context: Context, filename: String): ArrayList<String>

    fun close()

}