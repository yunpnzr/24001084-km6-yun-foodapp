package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.catalog

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CatalogResponse(
    @field:SerializedName("code")
    val code: Int? = null,
    @field:SerializedName("data")
    val data: List<CatalogItemResponse>? = null,
    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("status")
    val status: Boolean? = null,
)
