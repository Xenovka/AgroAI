package com.capstone.agroai

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.capstone.agroai.ml.TfLiteModelPotato
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

object TensorFlowHelper {

    val imageSize = 224

    fun classifyImage(context: Context, image: Bitmap, callback: (potato: String) -> Unit) {
        val model = TfLiteModelPotato.newInstance(context)

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(imageSize * imageSize)
        image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)

        var pixel = 0
        for(i in 0 until imageSize) {
            for(j in 0 until imageSize) {
                val value = intValues[pixel++]
                byteBuffer.putFloat((value shr 16 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((value shr 8 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((value and 0xFF) * (1f / 1))
            }
        }

        byteBuffer.rewind()
        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val confidences = outputFeature0.floatArray
        var maxPos = 0
        var maxConfidence = 0f
        for(i in confidences.indices) {
            if(confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }

        val classes = arrayOf("potato early blight", "potato healthy", "potato late blight")
        callback.invoke(classes[maxPos])

        // Releases model resources if no longer used.
        model.close()

    }
}