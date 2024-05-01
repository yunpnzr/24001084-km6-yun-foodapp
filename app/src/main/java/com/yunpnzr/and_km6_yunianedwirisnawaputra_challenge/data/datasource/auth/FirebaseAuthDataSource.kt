package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.auth

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.User
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.toUser
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.firebase.FirebaseServices

class FirebaseAuthDataSource(private val service: FirebaseServices) : AuthDataSource {
    override suspend fun doLogin(
        email: String,
        password: String,
    ): Boolean {
        return service.doLogin(email, password)
    }

    override suspend fun doRegister(
        name: String,
        email: String,
        password: String,
    ): Boolean {
        return service.doRegister(name, email, password)
    }

    override suspend fun updateProfile(name: String?): Boolean {
        return service.updateProfile(name)
    }

    override suspend fun updatePassword(newPassword: String): Boolean {
        return service.updatePassword(newPassword)
    }

    override suspend fun updateEmail(newEmail: String): Boolean {
        return service.updateEmail(newEmail)
    }

    override fun requestChangePasswordByEmail(): Boolean {
        return service.requestChangePasswordByEmail()
    }

    override fun doLogout(): Boolean {
        return service.doLogout()
    }

    override fun isLoggedIn(): Boolean {
        return service.isLoggedIn()
    }

    override fun getCurrentUser(): User? {
        return service.getCurrentUser().toUser()
    }
}
