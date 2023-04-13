package com.nkoyo.componentidentifier.domain.classifier

import android.content.Context
import android.graphics.Bitmap

interface ComponentClassifier {
    fun initialize()

    fun classify(bitmap: Bitmap): HashMap<String, String>

    fun loadData(context: Context, filename: String): ArrayList<String>

    fun close()

}