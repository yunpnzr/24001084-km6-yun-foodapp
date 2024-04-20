package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.User
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CatalogRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CategoryRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.PrefRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val catalogRepository: CatalogRepository,
    //private val userPreference: UserPreference
    private val prefRepository: PrefRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _isUsingGridMode = MutableLiveData(false)

    val isUsingGridMode: LiveData<Boolean>
        get() = _isUsingGridMode

    init {
        _isUsingGridMode.value = isUsingGridMode()
    }

    //fun getCatalogList() = catalogRepository.getCatalog()

    fun getCatalogList(category: String? = null) = catalogRepository.getCatalog(category).asLiveData(Dispatchers.IO)
    fun getCategoryList() = categoryRepository.getCategory().asLiveData(Dispatchers.IO)

    fun changeListMode(){
        val currentValue = _isUsingGridMode.value?:false
        _isUsingGridMode.postValue(!currentValue)
        setUsingGridMode(!currentValue)
    }

    private fun isUsingGridMode() = prefRepository.isUsingGridMode()
    private fun setUsingGridMode(isUsingGridMode: Boolean) =
        prefRepository.setUsingGridMode(isUsingGridMode)

    fun getUser(): User? {
        return userRepository.getCurrentUser()
    }

    fun isLoggedIn(): Boolean {
        return userRepository.isLoggedIn()
    }
}