package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.catalog

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.catalog.CatalogResponse

interface CatalogDataSource {
    //fun getCatalogDataSource(): List<Catalog>
    suspend fun getCatalogDataSource(category: String? = null): CatalogResponse
}