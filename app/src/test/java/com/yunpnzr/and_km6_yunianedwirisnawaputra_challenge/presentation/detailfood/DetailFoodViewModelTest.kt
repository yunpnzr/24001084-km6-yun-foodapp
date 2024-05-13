package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.detailfood

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Catalog
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CartRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.tools.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class DetailFoodViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var cartRepository: CartRepository

    @MockK
    lateinit var intent: Bundle

    private lateinit var viewModel: DetailFoodViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        val expectedCatalog =
            Catalog(
                "1",
                "das_reich",
                "himmler",
                35000.0,
                "Heil fuhrer",
                "Reichstag",
                "sdfsdfsdfdf",
            )
        every { intent.getParcelable<Catalog>(DetailFoodActivity.EXTRAS_ITEM) } returns expectedCatalog

        viewModel =
            spyk(
                DetailFoodViewModel(
                    intent, cartRepository,
                ),
            )
    }

    @Test
    fun getMenuCountLiveData() {
    }

    @Test
    fun getPriceLiveData() {
    }

    @Test
    fun addItem() {
    }

    @Test
    fun minusItem() {
    }

    @Test
    fun addToCart() {
    }
}
