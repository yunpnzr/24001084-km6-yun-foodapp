package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.checkout

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CartRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CatalogRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.UserRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.tools.MainCoroutineRule
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.tools.getOrAwaitValue
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CheckoutViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var cartRepository: CartRepository

    @MockK
    lateinit var userRepository: UserRepository

    @MockK
    lateinit var catalogRepository: CatalogRepository

    private lateinit var viewModel: CheckoutViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every { cartRepository.getCheckoutData() } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        Triple(
                            mockk(relaxed = true), mockk(relaxed = true), 0.0,
                        ),
                    ),
                )
            }

        viewModel =
            spyk(
                CheckoutViewModel(
                    cartRepository,
                    userRepository,
                    catalogRepository,
                ),
            )
    }

    @Test
    fun deleteAllCart() {
        every { cartRepository.deleteAll() } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.deleteAllCart()
        // verify { cartRepository.deleteAll() }
    }

    @Test
    fun checkoutCart() {
        val mockUsername = "hitler"
        every { userRepository.getCurrentUser()?.name } returns mockUsername
        val expected = ResultWrapper.Success(true)
        coEvery { catalogRepository.createOrder(any(), any(), any()) } returns
            flow {
                emit(expected)
            }
        val result = viewModel.checkoutCart().getOrAwaitValue()
        assertEquals(expected, result)
    }

    @Test
    fun isLoggedIn() {
        val isLoggedIn = true
        every { userRepository.isLoggedIn() } returns isLoggedIn
        val result = viewModel.isLoggedIn()
        assertEquals(isLoggedIn, result)
    }
}
