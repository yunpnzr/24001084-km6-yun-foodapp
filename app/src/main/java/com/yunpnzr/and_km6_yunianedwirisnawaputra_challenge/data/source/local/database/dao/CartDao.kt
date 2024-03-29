package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.local.database.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM food_cart")
    fun getAllCart(): Flow<List<CartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: CartEntity): Long

    @Update
    suspend fun updateCart(cart: CartEntity): Int

    @Delete
    suspend fun deleteCart(cart: CartEntity): Int

    @Query("DELETE FROM food_cart")
    fun deleteAll()
}