package com.example.quranapp.data.remote.api.models.azkar

import kotlinx.serialization.Serializable

@Serializable
data class SpecifiedAzkarModel(
    val id: Int,
    val text: String,
    val count: Int
)
