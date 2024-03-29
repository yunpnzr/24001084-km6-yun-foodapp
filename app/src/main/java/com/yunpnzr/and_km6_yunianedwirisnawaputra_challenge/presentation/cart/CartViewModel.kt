package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Cart
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CartRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class CartViewModel(
    private val cartRepository: CartRepository
): ViewModel() {
    fun getAllCarts(): LiveData<ResultWrapper<Pair<List<Cart>, Double>>> {
        return cartRepository.getUserCartData().asLiveData(Dispatchers.IO)
    }

    fun decreaseCart(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.decreaseCart(item).collect()
        }
    }
    fun increaseCart(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.increaseCart(item).collect()
        }
    }

    fun removeCart(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteCart(item).collect()
        }
    }
    fun setCartNote(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.setCartNotes(item).collect()
        }
    }
}