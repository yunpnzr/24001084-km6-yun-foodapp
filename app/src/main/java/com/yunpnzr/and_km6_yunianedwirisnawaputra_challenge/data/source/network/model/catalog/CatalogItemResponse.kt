package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.catalog

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CatalogItemResponse(
    @field:SerializedName("harga_format")
    val hargaFormat: String? = null,
    @field:SerializedName("nama")
    val nama: String? = null,
    @field:SerializedName("harga")
    val harga: Int? = null,
    @field:SerializedName("image_url")
    val imageUrl: String? = null,
    @field:SerializedName("detail")
    val detail: String? = null,
    @field:SerializedName("alamat_resto")
    val alamatResto: String? = null,
)
