package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.catalog

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.catalog.CatalogResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.checkout.CheckoutRequestResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.checkout.CheckoutResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.service.ApiService

class CatalogApiDataSource(
    private val service: ApiService,
) : CatalogDataSource {
    override suspend fun getCatalogDataSource(category: String?): CatalogResponse {
        return service.getCatalog(category)
    }

    override suspend fun createOrder(payload: CheckoutRequestResponse): CheckoutResponse {
        return service.createOrder(payload)
    }
}
