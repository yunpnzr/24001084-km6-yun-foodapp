package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.category

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.category.CategoryResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.service.ApiService

class CategoryApiDataSource(
    private val service: ApiService
): CategoryDataSource {
    override suspend fun getCategoryDataSource(): CategoryResponse {
        return service.getCategory()
    }

}