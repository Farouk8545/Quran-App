package com.example.quranapp.api.models.edition

data class EditionDataModel(
    val identifier: String,
    val language: String,
    val name: String,
    val englishName: String,
    val format: String,
    val type: String,
    val direction: String
)
