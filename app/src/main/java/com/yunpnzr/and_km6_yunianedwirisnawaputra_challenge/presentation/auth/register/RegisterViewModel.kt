package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.UserRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {
    fun doRegister(
        name: String,
        email: String,
        password: String,
    ): LiveData<ResultWrapper<Boolean>> {
        return repository.doRegister(name, email, password).asLiveData(Dispatchers.IO)
    }

    /*fun doRegister(email: String, name: String, password: String, phoneNumber: Long) =
        repository.doRegister(name, email, password, phoneNumber).asLiveData(Dispatchers.IO)*/
}
