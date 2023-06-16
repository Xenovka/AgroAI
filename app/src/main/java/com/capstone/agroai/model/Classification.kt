package com.capstone.agroai.model

data class Classification (
    val id: String,
    val plant: String,
    val diseaseName: String,
    val diseaseNameIndonesia: String,
    val definition: String,
    val causes: String
)