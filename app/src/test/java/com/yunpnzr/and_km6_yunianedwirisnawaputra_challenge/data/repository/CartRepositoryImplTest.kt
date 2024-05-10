package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository

import app.cash.turbine.test
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.cart.CartDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Cart
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Catalog
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.PriceItem
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.local.database.entity.CartEntity
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CartRepositoryImplTest {
    @MockK
    lateinit var dataSource: CartDataSource
    private lateinit var repository: CartRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CartRepositoryImpl(dataSource)
    }

    @Test
    fun `When get cart success`() {
        val entity1 =
            CartEntity(
                id = 1,
                menuId = "asdasds",
                menuName = "adfsdfsdf",
                menuImageUrl = "sdfsdfsdfd",
                menuPrice = 8000.0,
                itemQuantity = 3,
                itemNotes = "sdfsdfsdfsdf",
            )
        val entity2 =
            CartEntity(
                id = 2,
                menuId = "asdaasdsds",
                menuName = "adfsdfsdf",
                menuImageUrl = "sdasdfsdfsdfd",
                menuPrice = 10000.0,
                itemQuantity = 3,
                itemNotes = "sdfsdfasdsdfsdf",
            )
        val mockList = listOf(entity1, entity2)
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCart() } returns mockFlow
        runTest {
            repository.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockList.size, actualData.payload?.first?.size)
                assertEquals(54000.0, actualData.payload?.second)
                verify { dataSource.getAllCart() }
            }
        }
    }

    @Test
    fun `When get cart loading`() {
        val entity1 =
            CartEntity(
                id = 1,
                menuId = "asdasds",
                menuName = "adfsdfsdf",
                menuImageUrl = "sdfsdfsdfd",
                menuPrice = 8000.0,
                itemQuantity = 3,
                itemNotes = "sdfsdfsdfsdf",
            )
        val entity2 =
            CartEntity(
                id = 2,
                menuId = "asdaasdsds",
                menuName = "adfsdfsdf",
                menuImageUrl = "sdasdfsdfsdfd",
                menuPrice = 10000.0,
                itemQuantity = 3,
                itemNotes = "sdfsdfasdsdfsdf",
            )
        val mockList = listOf(entity1, entity2)
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCart() } returns mockFlow
        runTest {
            repository.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(110)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                verify { dataSource.getAllCart() }
            }
        }
    }

    @Test
    fun `When get cart error`() {
        every { dataSource.getAllCart() } returns
            flow {
                throw IllegalStateException("Error")
            }
        runTest {
            repository.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                verify { dataSource.getAllCart() }
            }
        }
    }

    @Test
    fun `When get cart empty`() {
        val mockList = listOf<CartEntity>()
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCart() } returns mockFlow
        runTest {
            repository.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Empty)
                verify { dataSource.getAllCart() }
            }
        }
    }

    @Test
    fun `When get checkout data success`() {
        val entity1 =
            CartEntity(
                id = 1,
                menuId = "asdasds",
                menuName = "adfsdfsdf",
                menuImageUrl = "sdfsdfsdfd",
                menuPrice = 8000.0,
                itemQuantity = 3,
                itemNotes = "sdfsdfsdfsdf",
            )
        val entity2 =
            CartEntity(
                id = 2,
                menuId = "asdaasdsds",
                menuName = "adfsdfsdf",
                menuImageUrl = "sdasdfsdfsdfd",
                menuPrice = 10000.0,
                itemQuantity = 3,
                itemNotes = "sdfsdfasdsdfsdf",
            )
        val priceItem1 =
            PriceItem(
                name = "adfsdfsdf",
                total = 24000.0,
            )
        val priceItem2 =
            PriceItem(
                name = "adfsdfsdf",
                total = 30000.0,
            )
        val mockList = listOf(entity1, entity2)
        val mockPrice = listOf(priceItem1, priceItem2)
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCart() } returns mockFlow
        runTest {
            repository.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockList.size, actualData.payload?.first?.size)
                assertEquals(mockPrice.size, actualData.payload?.second?.size)
                assertEquals(54000.0, actualData.payload?.third)
                verify { dataSource.getAllCart() }
            }
        }
    }

    @Test
    fun `When get checkout data loading`() {
        val entity1 =
            CartEntity(
                id = 1,
                menuId = "asdasds",
                menuName = "adfsdfsdf",
                menuImageUrl = "sdfsdfsdfd",
                menuPrice = 8000.0,
                itemQuantity = 3,
                itemNotes = "sdfsdfsdfsdf",
            )
        val entity2 =
            CartEntity(
                id = 2,
                menuId = "asdaasdsds",
                menuName = "adfsdfsdf",
                menuImageUrl = "sdasdfsdfsdfd",
                menuPrice = 10000.0,
                itemQuantity = 3,
                itemNotes = "sdfsdfasdsdfsdf",
            )
        val priceItem1 =
            PriceItem(
                name = "adfsdfsdf",
                total = 24000.0,
            )
        val priceItem2 =
            PriceItem(
                name = "adfsdfsdf",
                total = 30000.0,
            )
        val mockList = listOf(entity1, entity2)
        val mockPrice = listOf(priceItem1, priceItem2)
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCart() } returns mockFlow
        runTest {
            repository.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(1110)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                verify { dataSource.getAllCart() }
            }
        }
    }

    @Test
    fun `When get checkout data error`() {
        every { dataSource.getAllCart() } returns
            flow {
                throw IllegalStateException("Error")
            }
        runTest {
            repository.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                verify { dataSource.getAllCart() }
            }
        }
    }

    @Test
    fun `When get checkout data empty`() {
        val mockList = listOf<CartEntity>()
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCart() } returns mockFlow
        runTest {
            repository.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Empty)
                verify { dataSource.getAllCart() }
            }
        }
    }

    @Test
    fun `When create cart success`() {
        val mockProduct = mockk<Catalog>(relaxed = true)
        every { mockProduct.id } returns "123"
        coEvery { dataSource.insertCart(any()) } returns 1
        runTest {
            repository.createCart(mockProduct, 1, "asdasdasd")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(201)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Success)
                    assertEquals(true, actualData.payload)
                    coVerify { dataSource.insertCart(any()) }
                }
        }
    }

    @Test
    fun `When create cart success notes null`() {
        val mockProduct = mockk<Catalog>(relaxed = true)
        every { mockProduct.id } returns "123"
        coEvery { dataSource.insertCart(any()) } returns 1
        runTest {
            repository.createCart(mockProduct, 1)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(2201)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Success)
                    assertEquals(true, actualData.payload)
                    coVerify { dataSource.insertCart(any()) }
                }
        }
    }

    @Test
    fun `When create cart loading`() {
        val mockProduct = mockk<Catalog>(relaxed = true)
        every { mockProduct.id } returns "123"
        coEvery { dataSource.insertCart(any()) } returns 1
        runTest {
            repository.createCart(mockProduct, 1, "asdasdsad")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(110)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Loading)
                    coVerify { dataSource.insertCart(any()) }
                }
        }
    }

    @Test
    fun `When create cart error process`() {
        val mockProduct = mockk<Catalog>(relaxed = true)
        every { mockProduct.id } returns "123"
        coEvery { dataSource.insertCart(any()) } throws IllegalStateException("Error")
        runTest {
            repository.createCart(mockProduct, 1, "asdasdsad")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Error)
                    coVerify { dataSource.insertCart(any()) }
                }
        }
    }

    @Test
    fun `When create cart error product ID`() {
        val mockProduct = mockk<Catalog>(relaxed = true)
        every { mockProduct.id } returns null
        coEvery { dataSource.insertCart(any()) } throws IllegalStateException("Error")
        runTest {
            repository.createCart(mockProduct, 1, "asdasdsad")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Error)
                    coVerify(atLeast = 0) { dataSource.insertCart(any()) }
                }
        }
    }

    @Test
    fun `When decrease cart more than 0`() {
        val mockCart =
            Cart(
                id = 1,
                menuId = "asdasds",
                menuName = "adfsdfsdf",
                menuImageUrl = "sdfsdfsdfd",
                menuPrice = 8000.0,
                itemQuantity = 3,
                itemNotes = "sdfsdfsdfsdf",
            )
        coEvery { dataSource.deleteCart(any()) } returns 1
        coEvery { dataSource.updateCart(any()) } returns 1
        runTest {
            repository.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 0) { dataSource.deleteCart(any()) }
                coVerify(atLeast = 1) { dataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun `When decrease cart less than 1`() {
        val mockCart =
            Cart(
                id = 1,
                menuId = "asdasds",
                menuName = "adfsdfsdf",
                menuImageUrl = "sdfsdfsdfd",
                menuPrice = 8000.0,
                itemQuantity = 1,
                itemNotes = "sdfsdfsdfsdf",
            )
        coEvery { dataSource.deleteCart(any()) } returns 1
        coEvery { dataSource.updateCart(any()) } returns 1
        runTest {
            repository.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { dataSource.deleteCart(any()) }
                coVerify(atLeast = 0) { dataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun `increase cart more than 0`() {
        val mockCart =
            Cart(
                id = 1,
                menuId = "asdasds",
                menuName = "adfsdfsdf",
                menuImageUrl = "sdfsdfsdfd",
                menuPrice = 8000.0,
                itemQuantity = 3,
                itemNotes = "sdfsdfsdfsdf",
            )
        coEvery { dataSource.updateCart(any()) } returns 1
        runTest {
            repository.increaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { dataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun setCartNotes() {
        val mockCart =
            Cart(
                id = 1,
                menuId = "asdasds",
                menuName = "adfsdfsdf",
                menuImageUrl = "sdfsdfsdfd",
                menuPrice = 8000.0,
                itemQuantity = 3,
                itemNotes = "sdfsdfsdfsdf",
            )
        coEvery { dataSource.updateCart(any()) } returns 1
        runTest {
            repository.setCartNotes(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { dataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun deleteCart() {
        val mockCart =
            Cart(
                id = 1,
                menuId = "asdasds",
                menuName = "adfsdfsdf",
                menuImageUrl = "sdfsdfsdfd",
                menuPrice = 8000.0,
                itemQuantity = 3,
                itemNotes = "sdfsdfsdfsdf",
            )
        coEvery { dataSource.deleteCart(any()) } returns 1
        runTest {
            repository.deleteCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { dataSource.deleteCart(any()) }
            }
        }
    }

    @Test
    fun deleteAll() {
        coEvery { dataSource.deleteAll() } returns Unit

        runTest {
            repository.deleteAll().map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertTrue(actualData.payload ?: false)
                coVerify(atLeast = 1) { dataSource.deleteAll() }
            }
        }
    }
}
