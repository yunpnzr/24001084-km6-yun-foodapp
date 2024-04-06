package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CatalogRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CategoryRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.local.pref.UserPreference

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val catalogRepository: CatalogRepository,
    private val userPreference: UserPreference
): ViewModel() {

    private val _isUsingGridMode = MutableLiveData(false)
    val isUsingGridMode: LiveData<Boolean>
        get() = _isUsingGridMode

    init {
        _isUsingGridMode.value = userPreference.isUsingGridMode()
    }

    fun getCatalogList() = catalogRepository.getCatalog()
    fun getCategoryList() = categoryRepository.getCategory()

    fun changeListMode(){
        val currentValue = _isUsingGridMode.value?:false
        _isUsingGridMode.postValue(!currentValue)
        userPreference.setUsingGridMode(!currentValue)
    }

}