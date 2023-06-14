package com.capstone.agroai

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.capstone.agroai.tensor.CornClassification
import com.capstone.agroai.tensor.PotatoClassification
import com.capstone.agroai.tensor.TeaClassification

object Helpers {
    fun classificationHelper(context: Context, plantType: String, image: Bitmap) : String {
        val classificationResult = mutableStateOf("")

        when(plantType) {
            "Jagung" -> {
                CornClassification.classifyImage(context = context, image = image) { disease ->
                    classificationResult.value = disease
                    Log.i("imageclassified", "Image is classified as: ${classificationResult.value}")
                }
            }
            "Kentang" -> {
                PotatoClassification.classifyImage(context = context, image = image) { disease ->
                    classificationResult.value = disease
                    Log.i("imageclassified", "Image is classified as: ${classificationResult.value}")
                }
            }
            "Teh" -> {
                TeaClassification.classifyImage(context = context, image = image) { disease ->
                    classificationResult.value = disease
                    Log.i("imageclassified", "Image is classified as: ${classificationResult.value}")
                }
            }
        }

        return classificationResult.value
    }
}