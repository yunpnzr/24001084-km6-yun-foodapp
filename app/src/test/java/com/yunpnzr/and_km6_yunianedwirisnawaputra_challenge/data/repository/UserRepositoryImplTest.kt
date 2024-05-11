package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository

import app.cash.turbine.test
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.auth.AuthDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.User
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {
    @MockK
    lateinit var dataSource: AuthDataSource
    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = UserRepositoryImpl(dataSource)
    }

    @Test
    fun doLogin() {
        val email = "hitler@gmail.com"
        val password = "reich"
        runTest {
            coEvery { dataSource.doLogin(email, password) } returns true
            repository.doLogin(email, password).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.doLogin(email, password) }
            }
        }
    }

    @Test
    fun doRegister() {
        val name = "Hitler"
        val email = "hitler@gmail.com"
        val password = "hitler123"
        runTest {
            coEvery { dataSource.doRegister(name, email, password) } returns true
            repository.doRegister(name, email, password).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.doRegister(name, email, password) }
            }
        }
    }

    @Test
    fun updateProfile() {
        runTest {
            coEvery { dataSource.updateProfile(any()) } returns true
            repository.updateProfile().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.updateProfile(any()) }
            }
        }
    }

    @Test
    fun updatePassword() {
        val password = "konigstiger"
        runTest {
            coEvery { dataSource.updatePassword(password) } returns true
            repository.updatePassword(password).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.updatePassword(password) }
            }
        }
    }

    @Test
    fun updateEmail() {
        val email = "himmler@gmail.com"
        runTest {
            coEvery { dataSource.updateEmail(email) } returns true
            repository.updateEmail(email).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.updateEmail(email) }
            }
        }
    }

    @Test
    fun requestChangePasswordByEmail() {
        runTest {
            every { dataSource.requestChangePasswordByEmail() } returns true
            val result = repository.requestChangePasswordByEmail()
            verify { dataSource.requestChangePasswordByEmail() }
            Assert.assertEquals(true, result)
        }
    }

    @Test
    fun doLogout() {
        runTest {
            every { dataSource.doLogout() } returns true
            val result = repository.doLogout()
            verify { dataSource.doLogout() }
            assertEquals(true, result)
        }
    }

    @Test
    fun isLoggedIn() {
        runTest {
            every { dataSource.isLoggedIn() } returns true
            val result = repository.isLoggedIn()
            verify { dataSource.isLoggedIn() }
            assertEquals(true, result)
        }
    }

    @Test
    fun getCurrentUser() {
        val user =
            User(
                "1",
                "Hitler",
                "hitler@gmail.com",
                null,
                null,
            )
        runTest {
            every { dataSource.getCurrentUser() } returns user
            val result = repository.getCurrentUser()
            assertEquals(user, result)
            verify { dataSource.getCurrentUser() }
        }
    }
}
