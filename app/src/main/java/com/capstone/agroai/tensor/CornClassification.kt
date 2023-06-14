package com.capstone.agroai.tensor

import android.content.Context
import android.graphics.Bitmap
import com.capstone.agroai.ml.TfLiteModelPotato
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class CornClassification {
    companion object {
        fun classifyImage(context: Context, image: Bitmap, callback: (corn: String) -> Unit) {
            val model = TfLiteModelPotato.newInstance(context)

            // Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3)
            byteBuffer.order(ByteOrder.nativeOrder())

            val intValues = IntArray(224 * 224)
            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)

            var pixel = 0
            for(i in 0 until 224) {
                for(j in 0 until 224) {
                    val value = intValues[pixel++]
                    byteBuffer.putFloat((value shr 16 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((value shr 8 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((value and 0xFF) * (1f / 1))
                }
            }

            byteBuffer.rewind()
            inputFeature0.loadBuffer(byteBuffer)

            // Runs the model and get result
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            val confidences = outputFeature0.floatArray
            var maxPos = 0
            var maxConfidence = 0.9f
            for(i in confidences.indices) {
                if(confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }

            val classes = arrayOf("corn Common Rust",  "corn Leaf Gray Leaf Spot",  "corn NorthernLeaf Blight",  "corn healthy")
            callback.invoke(classes[maxPos])

            // Releases model resources if no longer used.
            model.close()
        }
    }
}