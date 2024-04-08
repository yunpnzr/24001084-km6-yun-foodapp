package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.catalog.CatalogDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.mapper.toCatalogs
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Catalog
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.ResultWrapper
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {
    //fun getCatalog(): List<Catalog>
    fun getCatalog(category: String? = null): Flow<ResultWrapper<List<Catalog>>>
}

class CatalogRepositoryImpl(private val dataSource: CatalogDataSource): CatalogRepository{
    //override fun getCatalog(): List<Catalog> = dataSource.getCatalogDataSource()
    override fun getCatalog(category: String?): Flow<ResultWrapper<List<Catalog>>> {
        return proceedFlow {
            dataSource.getCatalogDataSource(category).data.toCatalogs()
        }
    }

}

//baru sampe repository rombaknya