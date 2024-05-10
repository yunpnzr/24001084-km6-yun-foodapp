package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.pref.PrefDataSource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PrefRepositoryImplTest {
    @MockK
    lateinit var dataSource: PrefDataSource
    private lateinit var repository: PrefRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = PrefRepositoryImpl(dataSource)
    }

    @Test
    fun isUsingGridMode() {
        runTest {
            every { repository.isUsingGridMode() } returns true
            val result = dataSource.isUsingGridMode()
            verify { repository.isUsingGridMode() }
            assertEquals(true, result)
        }
    }

    @Test
    fun setUsingGridMode() {
        runTest {
            val seUsingGridMode = true
            every { repository.setUsingGridMode(seUsingGridMode) } returns Unit
            val result = dataSource.setUsingGridMode(seUsingGridMode)
            verify { repository.setUsingGridMode(seUsingGridMode) }
            assertEquals(Unit, result)
        }
    }
}
