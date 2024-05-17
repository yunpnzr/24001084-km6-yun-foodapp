package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.User
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CatalogRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CategoryRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.PrefRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.UserRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.tools.MainCoroutineRule
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.tools.getOrAwaitValue
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var categoryRepository: CategoryRepository

    @MockK
    lateinit var catalogRepository: CatalogRepository

    @MockK
    lateinit var prefRepository: PrefRepository

    @MockK
    lateinit var userRepository: UserRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { prefRepository.isUsingGridMode() } returns true
        every { prefRepository.setUsingGridMode(any()) } returns Unit
        viewModel =
            spyk(
                HomeViewModel(
                    categoryRepository,
                    catalogRepository,
                    prefRepository,
                    userRepository,
                ),
            )
    }

    @Test
    fun `isUsingGridMode should return true`() =
        runTest {
            val expectedValue = true
            val actualValue = viewModel.isUsingGridMode.value
            assertEquals(expectedValue, actualValue)
        }

    @Test
    fun getCatalogList() {
        every { catalogRepository.getCatalog() } returns
            flow {
                emit(
                    ResultWrapper.Success(listOf(mockk(relaxed = true), mockk(relaxed = true))),
                )
            }
        val result = viewModel.getCatalogList().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
    }

    @Test
    fun getCategoryList() {
        every { categoryRepository.getCategory() } returns
            flow {
                emit(
                    ResultWrapper.Success(listOf(mockk(relaxed = true), mockk(relaxed = true))),
                )
            }
        val result = viewModel.getCategoryList().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
    }

    @Test
    fun changeListMode() {
        runTest {
            val currentValue = viewModel.isUsingGridMode.value ?: false
            val expectedNewValue = !currentValue
            viewModel.changeListMode()
            assertEquals(expectedNewValue, viewModel.isUsingGridMode.value)
        }
    }

    @Test
    fun getUser() {
        val user =
            User(
                "1",
                "Hitler",
                "hitler@gmail.com",
                null,
                null,
            )
        // runTest {
        every { userRepository.getCurrentUser() } returns user
        val result = viewModel.getUser()
        assertEquals(user, result)
        // }
    }

    @Test
    fun isLoggedIn() {
        val isLoggedIn = true
        every { userRepository.isLoggedIn() } returns isLoggedIn
        val result = viewModel.isLoggedIn()
        assertEquals(isLoggedIn, result)
    }
}
