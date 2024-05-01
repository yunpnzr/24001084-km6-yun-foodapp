package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.category

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CategoryItemResponse(
    @field:SerializedName("nama")
    val nama: String? = null,
    @field:SerializedName("image_url")
    val imageUrl: String? = null,
)
