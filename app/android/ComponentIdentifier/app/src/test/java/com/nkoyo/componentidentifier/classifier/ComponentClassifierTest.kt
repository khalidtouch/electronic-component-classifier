package com.nkoyo.componentidentifier.classifier

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.nkoyo.componentidentifier.classifier.classifier.ComponentClassifierFake
import com.nkoyo.componentidentifier.data.classifier.ComponentClassifierImpl
import com.nkoyo.componentidentifier.domain.classifier.ComponentClassifier
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ComponentClassifierTest {
    lateinit var classifier: ComponentClassifierFake

    @Before
    fun setup() {
        classifier = ComponentClassifierFake()
    }
    @Test
    fun getMaxResultFromFloatArray_whenFloatArrayIsNotEmpty_producesHighestValueIndexAsFirst() {
        val array = floatArrayOf(34f, 12f, 9f, 41f, 68f)
        val indexArray = classifier.getMaxResultFromFloatArray(array)
        assertEquals(indexArray[0], 4)
    }

}