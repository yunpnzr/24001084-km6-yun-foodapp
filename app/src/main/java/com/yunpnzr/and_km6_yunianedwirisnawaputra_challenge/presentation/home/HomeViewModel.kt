package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.AppDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.AppDataSourceImpl

class HomeViewModel: ViewModel() {

    private val dataSource: AppDataSource by lazy { AppDataSourceImpl() }
    private val _isUsingGridMode = MutableLiveData(false)
    val isUsingGridMode: LiveData<Boolean>
        get() = _isUsingGridMode

    fun getCategoryList() = dataSource.getCategoryDataSource()
    fun getCatalogList() = dataSource.getCatalogDataSource()
    fun changeListMode(){
        val currentValue = _isUsingGridMode.value?:false
        _isUsingGridMode.postValue(!currentValue)
    }
}