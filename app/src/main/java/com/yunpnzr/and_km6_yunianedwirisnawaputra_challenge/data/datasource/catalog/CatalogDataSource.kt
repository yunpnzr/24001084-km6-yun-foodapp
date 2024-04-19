package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.catalog

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.catalog.CatalogResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.checkout.CheckoutRequestResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.checkout.CheckoutResponse

interface CatalogDataSource {
    //fun getCatalogDataSource(): List<Catalog>
    suspend fun getCatalogDataSource(category: String? = null): CatalogResponse
    suspend fun createOrder(payload: CheckoutRequestResponse): CheckoutResponse
}