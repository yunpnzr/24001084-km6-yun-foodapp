package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.UserRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun doLogin(
        email: String,
        password: String,
    ): LiveData<ResultWrapper<Boolean>> {
        return repository.doLogin(email, password).asLiveData(Dispatchers.IO)
    }
}
