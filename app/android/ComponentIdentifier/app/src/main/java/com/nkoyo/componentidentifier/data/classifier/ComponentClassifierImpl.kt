package com.nkoyo.componentidentifier.data.classifier

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.os.SystemClock
import com.nkoyo.componentidentifier.R
import com.nkoyo.componentidentifier.domain.classifier.ComponentClassifier
import com.nkoyo.componentidentifier.domain.extensions.appendPercent
import com.nkoyo.componentidentifier.ui.viewmodel.HighestProbabilityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.gpu.GpuDelegate
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.util.Scanner
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

const val MODEL_FILENAME = "mobilenet_v2_fine_tuned2.tflite"
const val MODEL_FILENAME_3 = "mobilenet_v2_naive_model.tflite"
const val MODEL_FILENAME_TEST = "mobilenet_v2_fine_tuned.tflite"
const val LABEL_FILENAME = "labels.txt"
const val LABEL_FILENAME_2 = "labels2.txt"
const val LABEL_FILENAME_4 = "labels3.txt"
const val MODEL_FILENAME_4 = "mobilenet_v2_naive_11model.tflite"
const val MODEL_FILENAME_5 = "mobilenet_v2_fine_tuned11.tflite"
const val LABEL_FILENAME_5 = "labels4.txt"
const val MODEL_FILENAME_6 = "mobilenet_v2_fine_tuned10_6.tflite"
const val LABEL_FILENAME_6 = "labels6.txt"


@Singleton
class ComponentClassifierImpl @Inject constructor(
    @ApplicationContext val context: Context,
) : ComponentClassifier {
    val TAG = "ComponentClassifierImpl"
    private var interpreter: Interpreter? = null
    private var gpuDelegate: GpuDelegate? = null
    private var imageBuffer: TensorImage? = null
    private var imageWidth = 0
    private var imageHeight = 0
    private var labels = ArrayList<String>()
    private var initializationState = false

    override fun initialize() {
        try {
            val assetManager = context.assets
            val model = loadModelFile(assetManager, MODEL_FILENAME_6) ?: return
            labels = loadLabelData(context, LABEL_FILENAME_6)
            val compatibilityList = CompatibilityList()
            val options = Interpreter.Options().apply {
                if (compatibilityList.isDelegateSupportedOnThisDevice) {
                    val delegateOptions = compatibilityList.bestOptionsForThisDevice
                    this.addDelegate(GpuDelegate(delegateOptions))
                } else this.numThreads = 4
            }

            val interpreter = Interpreter(model, options)
            val inputShape = interpreter.getInputTensor(0).shape()
            imageWidth = inputShape[1]
            imageHeight = inputShape[2]
            val imageDataType: DataType = interpreter.getInputTensor(0).dataType()
            imageBuffer = TensorImage(imageDataType)
            this.interpreter = interpreter
            initializationState = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun classify(bitmap: Bitmap): HashMap<String, String> {
        return try {
            check(initializationState) {
                "The TensorFlow Model has not being initialized"
            }
            imageBuffer = convertImageIntoTensorImage(bitmap)
            val output = Array(1) { FloatArray(labels.size) }


            val startTime = SystemClock.uptimeMillis()
            interpreter?.run(imageBuffer?.buffer, output) //interprete the image
            val endTime = SystemClock.uptimeMillis()

            var inferenceTime = endTime - startTime
            val indexArray = getMaxResultFromFloatArray(output[0])

            val firstProbability =
                (output.first()[indexArray[0]] * 100).roundToInt().toString().appendPercent()
            val secondProbability =
                (output.first()[indexArray[1]] * 100).roundToInt().toString().appendPercent()
            val thirdProbability =
                (output.first()[indexArray[2]] * 100).roundToInt().toString().appendPercent()
            hashMapOf(
                context.getString(R.string.prediction_one) to labels[indexArray[0]],
                context.getString(R.string.probability_one) to firstProbability,
                context.getString(R.string.prediction_two) to labels[indexArray[1]],
                context.getString(R.string.probability_two) to secondProbability,
                context.getString(R.string.prediction_three) to labels[indexArray[2]],
                context.getString(R.string.probability_three) to thirdProbability,
                context.getString(R.string.inference_time) to "$inferenceTime ms"
            )
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf<String, String>()
        }
    }

    override fun classifyAndProduceHighestProbabilityLabel(bitmap: Bitmap): Pair<String, Float> {
        return try {
            check(initializationState) {
                "The TensorFlow Model has not being initialized"
            }
            imageBuffer = convertImageIntoTensorImage(bitmap)
            val output = Array(1) { FloatArray(labels.size) }


            val startTime = SystemClock.uptimeMillis()
            interpreter?.run(imageBuffer?.buffer, output) //interprete the image
            val endTime = SystemClock.uptimeMillis()

            var inferenceTime = endTime - startTime
            val indexArray = getMaxResultFromFloatArray(output[0])

            val label = labels[indexArray[0]].lowercase()
            val highestProbability =
                (output.first()[indexArray[0]] * 100)
            Pair(label, highestProbability)
        } catch (e: Exception) {
            e.printStackTrace()
            Pair("", 0f)
        }
    }

    override fun loadLabelData(context: Context, filename: String): ArrayList<String> {
        return try {
            val data = Scanner(InputStreamReader(context.assets.open(filename)))
            val labels = ArrayList<String>()
            while (data.hasNextLine()) {
                labels.add(data.nextLine())
            }
            data.close()
            labels
        } catch (e: IOException) {
            e.printStackTrace()
            arrayListOf<String>()
        }
    }

    override fun close() {
        if (interpreter != null) {
            interpreter?.close()
            interpreter = null
        }
        if (gpuDelegate != null) {
            gpuDelegate?.close()
            gpuDelegate = null
        }
    }

    private fun loadModelFile(assetManager: AssetManager, filename: String): ByteBuffer? {
        return try {
            val fileDescriptor = assetManager.openFd(filename)
            val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
            val fileChannel = inputStream.channel
            val startOffset = fileDescriptor.startOffset
            val declaredLength = fileDescriptor.declaredLength
            return fileChannel.map(
                FileChannel.MapMode.READ_ONLY,
                startOffset,
                declaredLength
            )
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun convertImageIntoTensorImage(bitmap: Bitmap): TensorImage? {
        return try {
            imageBuffer?.load(bitmap)
            val imageProcessor = ImageProcessor.Builder()
                .add(ResizeOp(imageHeight, imageWidth, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                .add(NormalizeOp(IMAGE_MEAN, IMAGE_STD))
                .build()
            imageProcessor.process(imageBuffer)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getMaxResultFromFloatArray(array: FloatArray): IntArray {
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

    companion object {
        private const val TAG = "ComponentClassifierImpl"
        private const val FLOAT_TYPE_SIZE = 4
        private const val CHANNEL_SIZE = 3
        private const val IMAGE_MEAN = 0f
        private const val IMAGE_STD = 255f
    }
}
