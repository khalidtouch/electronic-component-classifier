package com.nkoyo.componentidentifier.classifier.classifier

class ComponentClassifierFake {
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