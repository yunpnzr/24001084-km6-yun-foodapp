package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.category

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.category.CategoryResponse

interface CategoryDataSource {
    suspend fun getCategoryDataSource(): CategoryResponse
}
