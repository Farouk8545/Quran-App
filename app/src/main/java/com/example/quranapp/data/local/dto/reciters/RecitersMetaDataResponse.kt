package com.example.quranapp.data.local.dto.reciters

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecitersMetaDataResponse(
    @SerialName("meta_data") val recitersMetaData: List<ReciterMetaData>
)
