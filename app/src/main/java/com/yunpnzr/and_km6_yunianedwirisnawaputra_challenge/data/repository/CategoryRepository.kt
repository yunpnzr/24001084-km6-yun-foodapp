package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.category.CategoryDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Category

interface CategoryRepository {
    fun getCategory(): List<Category>
}

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource): CategoryRepository{
    override fun getCategory(): List<Category> = dataSource.getCategoryDataSource()

}