package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.catalog

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Catalog

interface CatalogDataSource {
    fun getCatalogDataSource(): List<Catalog>
}