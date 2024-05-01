package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.cart

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.local.database.dao.CartDao
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.local.database.entity.CartEntity
import kotlinx.coroutines.flow.Flow

interface CartDataSource {
    fun getAllCart(): Flow<List<CartEntity>>

    suspend fun insertCart(cart: CartEntity): Long

    suspend fun updateCart(cart: CartEntity): Int

    suspend fun deleteCart(cart: CartEntity): Int

    fun deleteAll()
}

class CartDatabaseDataSource(
    private val dao: CartDao,
) : CartDataSource {
    override fun getAllCart(): Flow<List<CartEntity>> {
        return dao.getAllCart()
    }

    override suspend fun insertCart(cart: CartEntity): Long {
        return dao.insertCart(cart)
    }

    override suspend fun updateCart(cart: CartEntity): Int {
        return dao.updateCart(cart)
    }

    override suspend fun deleteCart(cart: CartEntity): Int {
        return dao.deleteCart(cart)
    }

    override fun deleteAll() {
        return dao.deleteAll()
    }
}
