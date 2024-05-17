package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.auth

import com.google.firebase.auth.FirebaseUser
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.firebase.FirebaseServices
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class FirebaseAuthDataSourceTest {
    @MockK
    lateinit var service: FirebaseServices
    private lateinit var authDataSource: AuthDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        authDataSource = FirebaseAuthDataSource(service)
    }

    @Test
    fun doLogin() {
        runTest {
            val email = "hitler@gmail.com"
            val password = "hitler123"
            coEvery { service.doLogin(email, password) } returns true
            val result = authDataSource.doLogin(email, password)
            coVerify { service.doLogin(email, password) }
            assertEquals(true, result)
        }
    }

    @Test
    fun doRegister() {
        runTest {
            val name = "Hitler"
            val email = "hitler@gmail.com"
            val password = "hitler123"
            coEvery { service.doRegister(name, email, password) } returns true
            val result = authDataSource.doRegister(name, email, password)
            coVerify { service.doRegister(name, email, password) }
            assertEquals(true, result)
        }
    }

    @Test
    fun updateProfile() {
        runTest {
            coEvery { service.updateProfile(any()) } returns true
            val result = authDataSource.updateProfile()
            coVerify { service.updateProfile(any()) }
            assertEquals(true, result)
        }
    }

    @Test
    fun updatePassword() {
        runTest {
            val password = "fuhrer"
            coEvery { service.updatePassword(password) } returns true
            val result = authDataSource.updatePassword(password)
            coVerify { service.updatePassword(password) }
            assertEquals(true, result)
        }
    }

    @Test
    fun updateEmail() {
        runTest {
            val email = "himmler@gmail.com"
            coEvery { service.updateEmail(email) } returns true
            val result = authDataSource.updateEmail(email)
            coVerify { service.updateEmail(email) }
            assertEquals(true, result)
        }
    }

    @Test
    fun requestChangePasswordByEmail() {
        runTest {
            every { service.requestChangePasswordByEmail() } returns true
            val result = authDataSource.requestChangePasswordByEmail()
            verify { service.requestChangePasswordByEmail() }
            assertEquals(true, result)
        }
    }

    @Test
    fun doLogout() {
        runTest {
            every { service.doLogout() } returns true
            val result = authDataSource.doLogout()
            verify { service.doLogout() }
            assertEquals(true, result)
        }
    }

    @Test
    fun isLoggedIn() {
        runTest {
            every { service.isLoggedIn() } returns true
            val result = authDataSource.isLoggedIn()
            verify { service.isLoggedIn() }
            assertEquals(true, result)
        }
    }

    @Test
    fun getCurrentUser() {
        runTest {
            val user = mockk<FirebaseUser>()
            every { service.getCurrentUser() } returns user
            every { user.uid } answers { "id" }
            every { user.displayName } answers { "Hitler" }
            every { user.email } answers { "hitler@gmail.com" }
            every { user.photoUrl } returns mockk(relaxed = true)
            every { user.phoneNumber } returns null

            val result = authDataSource.getCurrentUser()
            verify { service.getCurrentUser() }
            assertEquals("id", result?.id)
            assertEquals("Hitler", result?.name)
            assertEquals("hitler@gmail.com", result?.email)
            assertNotNull(result?.imgProfile)
            assertNull(result?.phoneNumber)
        }
    }
}
