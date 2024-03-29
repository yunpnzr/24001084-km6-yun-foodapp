package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.detailfood

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Catalog
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CartRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class DetailFoodViewModel(
    intent: Bundle?,
    private val cartRepository: CartRepository
): ViewModel() {
    val menuCatalog = intent?.getParcelable<Catalog>(DetailFoodActivity.EXTRAS_ITEM)
    val menuCountLiveData = MutableLiveData(0).apply {
        postValue(0)
    }

    val priceLiveData = MutableLiveData<Double>().apply {
        postValue(0.0)
    }
    fun addItem(){
        val count = (menuCountLiveData.value?:0)+1
        menuCountLiveData.postValue(count)
        priceLiveData.postValue(menuCatalog?.price?.times(count) ?: 0.0)
    }

    fun minusItem(){
        if ((menuCountLiveData.value ?: 0) > 0) {
            val count = (menuCountLiveData.value?:0)-1
            menuCountLiveData.postValue(count)
            priceLiveData.postValue(menuCatalog?.price?.times(count) ?: 0.0)
        }
    }

    fun addToCart(): LiveData<ResultWrapper<Boolean>>{
        return menuCatalog?.let {
            val quantity = menuCountLiveData.value ?: 0
            cartRepository.createCart(it, quantity).asLiveData(Dispatchers.IO)
        } ?: liveData {emit(ResultWrapper.Error(IllegalStateException("Product not found")))}
    }
}