package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo("menu_id")
    var menuId: String? = null,
    @ColumnInfo("menu_name")
    var menuName: String,
    @ColumnInfo("menu_price")
    var menuPrice: Double,
    @ColumnInfo("menu_image_url")
    var menuImageUrl: String,
    @ColumnInfo("item_quantity")
    val itemQuantity: Int = 0,
    @ColumnInfo("item_notes")
    val itemNotes: String? = null
)
