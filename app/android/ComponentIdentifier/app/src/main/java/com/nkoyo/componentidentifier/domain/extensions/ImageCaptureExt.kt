package com.nkoyo.componentidentifier.domain.extensions

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


suspend fun ImageCapture.takeSnapshot(executor: Executor): File {
    val photoFile = withContext(Dispatchers.IO){
        kotlin.runCatching {
            File.createTempFile("image", "jpg")
        }.getOrElse { e ->
            e.printStackTrace()
            File("/dev/null")
        }
    }

    return suspendCoroutine { continuation ->
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        takePicture(outputOptions, executor, object: ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
               continuation.resume(photoFile)
            }

            override fun onError(exception: ImageCaptureException) {
               continuation.resumeWithException(exception)
            }

        })
    }
}