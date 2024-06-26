package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.main

import androidx.lifecycle.ViewModel
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.UserRepository

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }
}
