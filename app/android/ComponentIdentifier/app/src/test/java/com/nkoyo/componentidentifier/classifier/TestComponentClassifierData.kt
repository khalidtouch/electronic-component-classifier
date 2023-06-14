package com.nkoyo.componentidentifier.classifier

class TestComponentClassifierData {
    val arrayOfFloat = floatArrayOf(10f, 12f, 14f, 16f, 25f)

    fun getMaxResultFromFloatArray(array: FloatArray): IntArray {
        val sortedArray = array.sortedDescending()
        val indexMapper = hashMapOf<Float, Int>()
        array.forEachIndexed { index, value ->
            indexMapper[value] = index
        }
        //get the indices of the elements with the highest probability
        val indexArray = IntArray(3)
        indexArray.forEachIndexed { index, _ ->
            indexArray[index] = indexMapper[sortedArray[index]]!!
        }
        return indexArray
    }
}