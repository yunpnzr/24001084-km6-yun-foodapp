package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.User
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.UserRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.tools.MainCoroutineRule
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.tools.getOrAwaitValue
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ProfileViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repository: UserRepository

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(ProfileViewModel(repository))
    }

    @Test
    fun changeEditMode() {
        val enabledEdit = true
        viewModel.changeEditMode()
        val enabledEditValue = viewModel.isEnableOrDisableEdit.value
        assertEquals(enabledEdit, enabledEditValue)

        val disabledEdit = false
        viewModel.changeEditMode()
        val disabledEditValue = viewModel.isEnableOrDisableEdit.value
        assertEquals(disabledEdit, disabledEditValue)
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
        every { repository.getCurrentUser() } returns user
        val result = viewModel.getCurrentUser()
        assertEquals(user, result)
    }

    @Test
    fun doChangeProfile() {
        val name = "Bung Towel"
        every { repository.updateProfile(name) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        val result = viewModel.doChangeProfile(name).getOrAwaitValue()
        val payload = (result as ResultWrapper.Success).payload
        assertEquals(true, payload)
    }

    @Test
    fun doChangePasswordByEmail() {
        val doChangePasswordByEmail = true
        every { repository.requestChangePasswordByEmail() } returns doChangePasswordByEmail
        val result = viewModel.doChangePasswordByEmail()
        assertEquals(doChangePasswordByEmail, result)
    }

    @Test
    fun doLogout() {
        val doLogout = true
        every { repository.doLogout() } returns doLogout
        val result = viewModel.doLogout()
        assertEquals(doLogout, result)
    }
}
