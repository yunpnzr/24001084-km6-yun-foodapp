package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.home

import androidx.lifecycle.ViewModel
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.AppDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.AppDataSourceImpl

class HomeViewModel: ViewModel() {

    private val dataSource: AppDataSource by lazy { AppDataSourceImpl() }

    fun getCategoryList() = dataSource.getCategoryDataSource()
    fun getCatalogList() = dataSource.getCatalogDataSource()

}