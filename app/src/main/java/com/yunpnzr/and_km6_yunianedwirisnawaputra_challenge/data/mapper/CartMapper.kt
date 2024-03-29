package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.mapper

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Cart
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.local.database.entity.CartEntity

fun Cart?.toCartEntity() = CartEntity(
    id = this?.id,
    menuId = this?.menuId.orEmpty(),
    menuName = this?.menuName.orEmpty(),
    menuPrice = this?.menuPrice ?: 0.0,
    menuImageUrl = this?.menuImageUrl.orEmpty(),
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes
)

fun CartEntity?.toCart() = Cart(
    id = this?.id,
    menuId = this?.menuId.orEmpty(),
    menuName = this?.menuName.orEmpty(),
    menuPrice = this?.menuPrice ?: 0.0,
    menuImageUrl = this?.menuImageUrl.orEmpty(),
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes
)

fun List<CartEntity?>.toCartList() = this.map {
    it.toCart()
}