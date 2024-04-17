package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.auth

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.User

interface AuthDataSource {
    @Throws(exceptionClasses = [Exception::class])
    suspend fun doLogin(
        email: String,
        password: String
    ): Boolean

    @Throws(exceptionClasses = [Exception::class])
    suspend fun doRegister(
        name: String,
        email: String,
        password: String,
        phoneNumber: String
    ): Boolean

    suspend fun updateProfile(name: String? = null): Boolean
    suspend fun updatePassword(newPassword: String): Boolean
    suspend fun updateEmail(newEmail: String): Boolean
    fun requestChangePasswordByEmail(): Boolean
    fun doLogout(): Boolean
    fun isLoggedIn(): Boolean
    fun getCurrentUser(): User?
}