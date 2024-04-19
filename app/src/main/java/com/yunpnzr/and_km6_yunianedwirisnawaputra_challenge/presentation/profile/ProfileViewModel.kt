package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.User
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.UserRepository

class ProfileViewModel(private val repository: UserRepository): ViewModel() {
    private val _isEnableOrDisableEdit = MutableLiveData(false)
    val isEnableOrDisableEdit: LiveData<Boolean>
        get() = _isEnableOrDisableEdit

    fun changeEditMode(){
        val currentValue = isEnableOrDisableEdit.value?: false
        _isEnableOrDisableEdit.postValue(!currentValue)
    }

    fun getCurrentUser(): User? {
        return repository.getCurrentUser()
    }

    fun doLogout(): Boolean {
        return repository.doLogout()
    }
}