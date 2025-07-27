package com.example.quranapp.api.models.edition

data class ApiEditionResponseModel(
    val code: Int,
    val status: String,
    val data: List<EditionDataModel>
)
