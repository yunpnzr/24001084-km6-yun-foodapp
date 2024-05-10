package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.catalog

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.catalog.CatalogResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.checkout.CheckoutRequestResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.checkout.CheckoutResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.service.ApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CatalogApiDataSourceTest {
    @MockK
    lateinit var service: ApiService
    private lateinit var dataSource: CatalogDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = CatalogApiDataSource(service)
    }

    @Test
    fun getCatalogDataSource() {
        runTest {
            val mockResponse = mockk<CatalogResponse>(relaxed = true)
            coEvery { service.getCatalog(any()) } returns mockResponse
            val actualResult = dataSource.getCatalogDataSource()
            coVerify { service.getCatalog(any()) }
            assertEquals(mockResponse, actualResult)
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockRequest = mockk<CheckoutRequestResponse>()
            val mockResponse = mockk<CheckoutResponse>(relaxed = true)
            coEvery { service.createOrder(any()) } returns mockResponse
            val actualResult = dataSource.createOrder(mockRequest)
            coVerify { service.createOrder(any()) }
            assertEquals(mockResponse, actualResult)
        }
    }
}
