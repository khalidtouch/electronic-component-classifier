package com.nkoyo.componentidentifier.data.classifier

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import com.nkoyo.componentidentifier.domain.classifier.ComponentClassifier
import dagger.hilt.android.qualifiers.ApplicationContext
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.gpu.GpuDelegate
import org.tensorflow.lite.support.image.TensorImage
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComponentClassifierImpl @Inject constructor(
    @ApplicationContext context: Context,
) : ComponentClassifier {
    private var interpreter: Interpreter? = null
    private var gpuDelegate: GpuDelegate? = null
    private var imageBuffer: TensorImage? = null
    private var imageWidth = 0
    private var imageHeight = 0

    override fun initialize() {
        TODO("Not yet implemented")
    }

    override fun classify(bitmap: Bitmap): HashMap<String, String> {
        TODO("Not yet implemented")
    }

    override fun loadData(context: Context, filename: String): ArrayList<String> {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
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
}