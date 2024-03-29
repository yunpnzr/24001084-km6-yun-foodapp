package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.detailfood

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.R
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.cart.CartDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.cart.CartDatabaseDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Catalog
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CartRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CartRepositoryImpl
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.local.database.AppDatabase
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.ActivityDetailFoodBinding
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.GenericViewModelFactory
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.proceedWhen
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.toIndonesianFormat

class DetailFoodActivity : AppCompatActivity() {

    private val binding: ActivityDetailFoodBinding by lazy {
        ActivityDetailFoodBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailFoodViewModel by viewModels{
        val database = AppDatabase.getDatabase(this)
        val dataSource: CartDataSource = CartDatabaseDataSource(database.cartDao())
        val repository: CartRepository = CartRepositoryImpl(dataSource)
        GenericViewModelFactory.create(
            DetailFoodViewModel(intent?.extras, repository)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListener()
        bindMenu(viewModel.menuCatalog)
        observeData()
    }

    private fun setClickListener(){
        binding.layoutIvProduct.backDetail.setOnClickListener {
            onBackPressed()
        }

        binding.layoutCounterCart.addCartPlus.setOnClickListener {
            viewModel.addItem()
        }

        binding.layoutCounterCart.addCartMinus.setOnClickListener{
            viewModel.minusItem()
        }
        
        binding.layoutCounterCart.btnAddCart.setOnClickListener {
            addMenuToCart()
        }

        binding.layoutAddressCatalog.tvStoreLocation.setOnClickListener {
            viewModel.menuCatalog?.let { catalog ->
                getMap(catalog)
            }
        }
    }

    private fun addMenuToCart() {
        viewModel.addToCart().observe(this){
            it.proceedWhen  (
                doOnSuccess = {
                    Toast.makeText(this, "Add cart success", Toast.LENGTH_SHORT).show()
                    finish()
                },
                doOnError = {
                    Toast.makeText(this, "Add cart failed", Toast.LENGTH_SHORT).show()
                },
                doOnLoading = {
                    Toast.makeText(this, "Add cart loading", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun getMap(catalog: Catalog){
        startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(catalog.mapUrl)))
    }

    private fun bindMenu(menu: Catalog?) {
        menu?.let { item ->
            binding.layoutIvProduct.ivDetail.load(menu.imageUrl){
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
        viewModel.priceLiveData.observe(this) {
            binding.layoutCounterCart.btnAddCart.isEnabled = it != 0.0
            //binding.tvCalculatedProductPrice.text = it.toDollarFormat()
            binding.layoutCounterCart.btnAddCart.text = getString(R.string.show_price_add_cart_button)+it.toIndonesianFormat()
        }
        viewModel.menuCountLiveData.observe(this) {
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