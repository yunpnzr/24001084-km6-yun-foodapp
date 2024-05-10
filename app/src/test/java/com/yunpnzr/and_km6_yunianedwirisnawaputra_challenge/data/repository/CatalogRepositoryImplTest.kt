package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository

import app.cash.turbine.test
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.catalog.CatalogDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Cart
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.catalog.CatalogItemResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.catalog.CatalogResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.checkout.CheckoutItemRequestResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.checkout.CheckoutRequestResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.checkout.CheckoutResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CatalogRepositoryImplTest {
    @MockK
    lateinit var dataSource: CatalogDataSource
    private lateinit var repository: CatalogRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CatalogRepositoryImpl(dataSource)
    }

    @Test
    fun `when get catalog success`() {
        val catalog1 =
            CatalogItemResponse(
                hargaFormat = "asadasdsa",
                nama = "dfsdfsdf",
                harga = 8000,
                imageUrl = "fsdfsdfsdf",
                detail = "adfadfsdfsf",
                alamatResto = "asfsdfsdfd",
            )
        val catalog2 =
            CatalogItemResponse(
                hargaFormat = "dfgdfgdfg",
                nama = "sdfgsrdgs",
                harga = 9000,
                imageUrl = "sdfsfsd",
                detail = "sdfsdfdsf",
                alamatResto = "sdfsdfdf",
            )
        val mockListCatalog = listOf(catalog1, catalog2)
        val mockResponse = mockk<CatalogResponse>()
        every { mockResponse.data } returns mockListCatalog
        runTest {
            coEvery { dataSource.getCatalogDataSource(any()) } returns mockResponse
            repository.getCatalog().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.getCatalogDataSource(any()) }
            }
        }
    }

    @Test
    fun `when get catalog loading`() {
        val catalog1 =
            CatalogItemResponse(
                hargaFormat = "asadasdsa",
                nama = "dfsdfsdf",
                harga = 8000,
                imageUrl = "fsdfsdfsdf",
                detail = "adfadfsdfsf",
                alamatResto = "asfsdfsdfd",
            )
        val catalog2 =
            CatalogItemResponse(
                hargaFormat = "dfgdfgdfg",
                nama = "sdfgsrdgs",
                harga = 9000,
                imageUrl = "sdfsfsd",
                detail = "sdfsdfdsf",
                alamatResto = "sdfsdfdf",
            )
        val mockListCatalog = listOf(catalog1, catalog2)
        val mockResponse = mockk<CatalogResponse>()
        every { mockResponse.data } returns mockListCatalog
        runTest {
            coEvery { dataSource.getCatalogDataSource(any()) } returns mockResponse
            repository.getCatalog().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.getCatalogDataSource(any()) }
            }
        }
    }

    @Test
    fun `when get catalog error`() {
        runTest {
            coEvery { dataSource.getCatalogDataSource(any()) } throws IllegalStateException("Mock error")
            repository.getCatalog().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getCatalogDataSource(any()) }
            }
        }
    }

    @Test
    fun `when get catalog empty`() {
        val mockListCatalog = listOf<CatalogItemResponse>()
        val mockResponse = mockk<CatalogResponse>()
        every { mockResponse.data } returns mockListCatalog
        runTest {
            coEvery { dataSource.getCatalogDataSource(any()) } returns mockResponse
            repository.getCatalog().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Empty)
                coVerify { dataSource.getCatalogDataSource(any()) }
            }
        }
    }

    @Test
    fun `when create order success`() {
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
        val mockFlow =
            flow {
                emit(mockCart)
            }
        val mockItemRequest = listOf<CheckoutItemRequestResponse>()
        val mockRequest = listOf<CheckoutRequestResponse>()
        val mockResponse = mockk<CheckoutResponse>()
    }

    @Test
    fun `when create order loading`() {
    }

    @Test
    fun `when create order error`() {
    }

    @Test
    fun `when create order empty`() {
    }
}
