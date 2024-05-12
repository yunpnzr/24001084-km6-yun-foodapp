package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.checkout

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Cart
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CartRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CatalogRepository
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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
                emit(ResultWrapper.Success(mockk()))
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
        every { cartRepository.deleteCart(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.deleteAllCart()
        // verify { cartRepository.deleteCart(any()) }
    }

    @Test
    fun checkoutCart() {
        val mockCart =
            listOf(
                Cart(
                    id = 1,
                    menuId = "asdasdasd",
                    menuName = "sdfsdf",
                    menuImageUrl = "sdfsdfsd",
                    menuPrice = 10000.0,
                    itemQuantity = 2,
                    itemNotes = "dfgdsfgsddfg",
                ),
                Cart(
                    id = 2,
                    menuId = "sdfsadfsadf",
                    menuName = "dfgdfgd",
                    menuImageUrl = "dsfgsadfsa",
                    menuPrice = 20000.0,
                    itemQuantity = 1,
                    itemNotes = "sdfsdfsdf",
                ),
            )
        val totalPrice = 35
        val username = "hitler"
        every { catalogRepository.createOrder(username, mockCart, totalPrice) } returns
            flow {
                emit(
                    ResultWrapper.Success(true),
                )
            }
        val result = viewModel.checkoutCart().getOrAwaitValue()
        // Assert that the result is successful
        assertTrue(result is ResultWrapper.Success && result.payload == true)
    }

    @Test
    fun isLoggedIn() {
        val isLoggedIn = true
        every { userRepository.isLoggedIn() } returns isLoggedIn
        val result = viewModel.isLoggedIn()
        assertEquals(isLoggedIn, result)
    }
}
