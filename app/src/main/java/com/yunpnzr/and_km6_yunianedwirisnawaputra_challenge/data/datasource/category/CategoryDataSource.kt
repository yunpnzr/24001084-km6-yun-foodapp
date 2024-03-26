package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.category

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Category

interface CategoryDataSource {
    fun getCategoryDataSource(): List<Category>
}