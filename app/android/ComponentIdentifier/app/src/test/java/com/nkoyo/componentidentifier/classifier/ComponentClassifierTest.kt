package com.nkoyo.componentidentifier.classifier

import org.junit.Assert.assertEquals
import org.junit.Test

class ComponentClassifierTest {

    private val componentClassifierData = TestComponentClassifierData()

    @Test
    fun getMaxResultFromFloatArray_whenFloatArrayIsNotEmpty_producesHighestValueIndexAsFirst() {
        assertEquals(
            4,
            componentClassifierData.getMaxResultFromFloatArray(
                componentClassifierData.arrayOfFloat
            )[0]
        )
    }

}