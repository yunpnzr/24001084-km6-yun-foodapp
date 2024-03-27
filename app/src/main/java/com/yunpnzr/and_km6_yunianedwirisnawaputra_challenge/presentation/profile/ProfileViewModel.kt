package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel: ViewModel() {
    private val _isEnableOrDisableEdit = MutableLiveData(false)
    val isEnableOrDisableEdit: LiveData<Boolean>
        get() = _isEnableOrDisableEdit

    fun changeEditMode(){
        val currentValue = isEnableOrDisableEdit.value?: false
        _isEnableOrDisableEdit.postValue(!currentValue)
    }
}