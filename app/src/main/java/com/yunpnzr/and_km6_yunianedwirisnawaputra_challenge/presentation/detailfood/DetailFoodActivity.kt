package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.detailfood

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import coil.load
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.R
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Catalog
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.ActivityDetailFoodBinding
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.proceedWhen
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.toIndonesianFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFoodActivity : AppCompatActivity() {
    private val binding: ActivityDetailFoodBinding by lazy {
        ActivityDetailFoodBinding.inflate(layoutInflater)
    }

    private val detailViewModel: DetailFoodViewModel by viewModel {
        parametersOf(intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListener()
        bindMenu(detailViewModel.menuCatalog)
        observeData()
    }

    private fun setClickListener() {
        binding.layoutIvProduct.backDetail.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.layoutCounterCart.addCartPlus.setOnClickListener {
            detailViewModel.addItem()
        }

        binding.layoutCounterCart.addCartMinus.setOnClickListener {
            detailViewModel.minusItem()
        }

        binding.layoutCounterCart.btnAddCart.setOnClickListener {
            addMenuToCart()
        }

        binding.layoutAddressCatalog.tvStoreLocation.setOnClickListener {
            detailViewModel.menuCatalog?.let { catalog ->
                getMap(catalog)
            }
        }
    }

    private fun addMenuToCart() {
        detailViewModel.addToCart().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbDetail.isVisible = false
                    val alertSuccess =
                        AlertDialog.Builder(this).setMessage(getString(R.string.add_menu_success))
                            .setPositiveButton("Oke") { _, _ ->
                                finish()
                            }
                    alertSuccess.show()
                },
                doOnError = {
                    binding.pbDetail.isVisible = false
                    Toast.makeText(this, getString(R.string.add_cart_failed), Toast.LENGTH_SHORT).show()
                },
                doOnLoading = {
                    binding.pbDetail.isVisible = true
                },
            )
        }
    }

    private fun getMap(catalog: Catalog) {
        startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(catalog.mapUrl)))
    }

    private fun bindMenu(menu: Catalog?) {
        menu?.let { _ ->
            binding.layoutIvProduct.ivDetail.load(menu.imageUrl) {
                error(R.drawable.img_error)
                placeholder(R.drawable.img_loading_picture)
            }
            binding.layoutDetailCatalog.tvTitleDetail.text = menu.name
            binding.layoutDetailCatalog.tvPriceDetail.text = menu.price.toIndonesianFormat()
            binding.layoutDetailCatalog.tvDescDetail.text = menu.desc
            binding.layoutAddressCatalog.tvStoreLocation.text = menu.marketAddress
        }
    }

    private fun observeData() {
        detailViewModel.priceLiveData.observe(this) {
            binding.layoutCounterCart.btnAddCart.isEnabled = it != 0.0
            // binding.tvCalculatedProductPrice.text = it.toDollarFormat()
            // binding.layoutCounterCart.btnAddCart.text = getString(R.string.show_price_add_cart_button)+it.toIndonesianFormat()
            binding.layoutCounterCart.btnAddCart.text = getString(R.string.show_price_add_cart_button, it.toIndonesianFormat())
        }
        detailViewModel.menuCountLiveData.observe(this) {
            binding.layoutCounterCart.counterAddCart.text = it.toString()
        }
    }

    companion object {
        const val EXTRAS_ITEM = "EXTRAS_ITEM"
        /*fun startActivity(context: Context, menu: Catalog) {
            val intent = Intent(context, DetailFoodActivity::class.java)
            intent.putExtra(EXTRAS_ITEM, menu)
            context.startActivity(intent)
        }*/
    }
}
