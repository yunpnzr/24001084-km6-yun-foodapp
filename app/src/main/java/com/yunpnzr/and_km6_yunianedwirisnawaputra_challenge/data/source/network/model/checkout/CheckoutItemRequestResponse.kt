package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.checkout

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CheckoutItemRequestResponse(

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("harga")
    val harga: Int? = null,

    @field:SerializedName("qty")
    val qty: Int? = null,

    @field:SerializedName("catatan")
    val catatan: String? = null
)
