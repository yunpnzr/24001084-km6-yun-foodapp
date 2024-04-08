package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.checkout

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CheckoutRequestResponse(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("orders")
	val orders: List<CheckoutItemRequestResponse>? = null,

	@field:SerializedName("username")
	val username: String? = null
)


