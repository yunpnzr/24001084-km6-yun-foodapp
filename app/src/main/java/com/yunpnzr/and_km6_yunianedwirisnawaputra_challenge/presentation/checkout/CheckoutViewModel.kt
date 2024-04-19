package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CartRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CatalogRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val catalogRepository: CatalogRepository
): ViewModel() {
    val checkoutData = cartRepository.getCheckoutData().asLiveData(Dispatchers.IO)

    fun deleteAllCart(){
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteAll().collect()
        }
    }

    fun checkoutCart() = catalogRepository.createOrder(
        userRepository.getCurrentUser()?.name ?: "",
        checkoutData.value?.payload?.first.orEmpty(),
        checkoutData.value?.payload?.third?.toInt() ?: 0
    ).asLiveData(Dispatchers.IO)

    fun isLoggedIn(): Boolean {
        return userRepository.isLoggedIn()
    }
}