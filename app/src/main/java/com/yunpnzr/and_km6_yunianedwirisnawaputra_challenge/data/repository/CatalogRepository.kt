package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.catalog.CatalogDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Catalog

interface CatalogRepository {
    fun getCatalog(): List<Catalog>
}

class CatalogRepositoryImpl(private val dataSource: CatalogDataSource): CatalogRepository{
    override fun getCatalog(): List<Catalog> = dataSource.getCatalogDataSource()

}