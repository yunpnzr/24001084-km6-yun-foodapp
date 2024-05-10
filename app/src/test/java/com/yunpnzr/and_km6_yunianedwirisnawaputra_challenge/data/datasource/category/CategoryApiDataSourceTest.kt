package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.category

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.category.CategoryResponse
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

class CategoryApiDataSourceTest {
    @MockK
    lateinit var service: ApiService
    private lateinit var dataSource: CategoryDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = CategoryApiDataSource(service)
    }

    @Test
    fun getCategoryDataSource() {
        runTest {
            val mockResponse = mockk<CategoryResponse>(relaxed = true)
            coEvery { service.getCategory() } returns mockResponse
            val actualResult = dataSource.getCategoryDataSource()
            coVerify { service.getCategory() }
            assertEquals(mockResponse, actualResult)
        }
    }
}
