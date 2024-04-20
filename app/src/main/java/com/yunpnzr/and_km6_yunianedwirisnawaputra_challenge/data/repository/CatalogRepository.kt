package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.catalog.CatalogDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.mapper.toCatalogs
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Cart
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Catalog
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.checkout.CheckoutItemRequestResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.checkout.CheckoutRequestResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.ResultWrapper
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {
    fun getCatalog(category: String? = null): Flow<ResultWrapper<List<Catalog>>>
    fun createOrder(
        profile: String,
        cart: List<Cart>,
        totalPrice: Int
    ): Flow<ResultWrapper<Boolean>>
}

class CatalogRepositoryImpl(private val dataSource: CatalogDataSource): CatalogRepository{
    override fun getCatalog(category: String?): Flow<ResultWrapper<List<Catalog>>> {
        return proceedFlow {
            dataSource.getCatalogDataSource(category).data.toCatalogs()
        }
    }

    override fun createOrder(
        profile: String,
        cart: List<Cart>,
        totalPrice: Int
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.createOrder(CheckoutRequestResponse(
                username = profile,
                orders = cart.map {
                    CheckoutItemRequestResponse(
                        nama = it.menuName,
                        harga = it.menuPrice.toInt(),
                        qty = it.itemQuantity,
                        catatan = it.itemNotes
                    )
                },
                total = totalPrice
            )).status ?: false
        }
    }

}