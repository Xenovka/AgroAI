package com.capstone.agroai.data

import com.capstone.agroai.model.Classification
import com.capstone.agroai.model.ClassificationData

class Repository {
    private val classifications = mutableListOf<Classification>()

    init {
        if(classifications.isEmpty()) {
            ClassificationData.classes.forEach {
                classifications.add(it)
            }
        }
    }

    fun getClassificationByName(diseaseName: String): Classification {
        return classifications.first {
            it.diseaseName == diseaseName
        }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(): Repository =
            instance ?: synchronized(this) {
                Repository().apply {
                    instance = this
                }
            }
    }
}