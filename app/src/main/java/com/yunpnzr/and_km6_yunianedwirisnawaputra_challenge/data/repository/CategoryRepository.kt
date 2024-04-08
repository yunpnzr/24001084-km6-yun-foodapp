package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.category.CategoryDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.mapper.toCategories
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Category
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.ResultWrapper
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    //fun getCategory(): List<Category>
    fun getCategory(): Flow<ResultWrapper<List<Category>>>
}

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource): CategoryRepository{
    //override fun getCategory(): List<Category> = dataSource.getCategoryDataSource()
    override fun getCategory(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow {
            dataSource.getCategoryDataSource().data.toCategories()
        }
    }

}

//baru sampe repository rombaknya