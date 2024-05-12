package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.auth.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.UserRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.tools.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class LoginViewModelTest {

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repository: UserRepository

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(LoginViewModel(repository))
    }

    @Test
    fun doLogin() {
    }
}