package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model

data class Cart(
    var id: Int? = null,
    var menuId: String? = null,
    var menuName: String,
    var menuPrice: Double,
    var menuImageUrl: String,
    var itemQuantity: Int = 0,
    var itemNotes: String? = null,
)
