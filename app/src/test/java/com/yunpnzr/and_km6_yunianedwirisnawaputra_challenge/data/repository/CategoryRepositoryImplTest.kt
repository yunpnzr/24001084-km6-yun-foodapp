package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository

import app.cash.turbine.test
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.category.CategoryDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.category.CategoryItemResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.category.CategoryResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CategoryRepositoryImplTest {
    @MockK
    lateinit var dataSource: CategoryDataSource
    private lateinit var repository: CategoryRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CategoryRepositoryImpl(dataSource)
    }

    @Test
    fun `get category loading`() {
        val category1 =
            CategoryItemResponse(
                nama = "psu",
                imageUrl = "abc",
            )
        val category2 =
            CategoryItemResponse(
                nama = "psua",
                imageUrl = "abcd",
            )
        val mockListCategory = listOf(category1, category2)
        val mockResponse = mockk<CategoryResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { dataSource.getCategoryDataSource() } returns mockResponse
            repository.getCategory().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.getCategoryDataSource() }
            }
        }
    }

    @Test
    fun `get category success`() {
        val category1 =
            CategoryItemResponse(
                nama = "psu",
                imageUrl = "abc",
            )
        val category2 =
            CategoryItemResponse(
                nama = "psua",
                imageUrl = "abcd",
            )
        val mockListCategory = listOf(category1, category2)
        val mockResponse = mockk<CategoryResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { dataSource.getCategoryDataSource() } returns mockResponse
            repository.getCategory().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.getCategoryDataSource() }
            }
        }
    }

    @Test
    fun `get category error`() {
        runTest {
            coEvery { dataSource.getCategoryDataSource() } throws IllegalStateException("Mock error")
            repository.getCategory().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getCategoryDataSource() }
            }
        }
    }

    @Test
    fun `get category empty`() {
        val mockListCategory = listOf<CategoryItemResponse>()
        val mockResponse = mockk<CategoryResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { dataSource.getCategoryDataSource() } returns mockResponse
            repository.getCategory().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Empty)
                coVerify { dataSource.getCategoryDataSource() }
            }
        }
    }
}
