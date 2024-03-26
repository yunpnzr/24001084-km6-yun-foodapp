package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.detailfood

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.R
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Catalog
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.ActivityDetailFoodBinding
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.toIndonesianFormat

class DetailFoodActivity : AppCompatActivity() {

    private var qytCart = 1
    private var price: Double = 0.0

    private val binding: ActivityDetailFoodBinding by lazy {
        ActivityDetailFoodBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getData()
        getBackActivityDetail()
    }

    private fun showDataDetail(catalog: Catalog) {
        binding.layoutIvProduct.ivDetail.load(catalog.imageUrl){
            error(R.drawable.img_error)
            placeholder(R.drawable.img_loading_picture)
        }
        binding.layoutDetailCatalog.tvTitleDetail.text = catalog.name
        binding.layoutDetailCatalog.tvPriceDetail.text = catalog.price.toIndonesianFormat()
        binding.layoutDetailCatalog.tvDescDetail.text = catalog.desc
        binding.layoutAddressCatalog.tvStoreLocation.text = catalog.marketAddress

        binding.layoutAddressCatalog.tvStoreLocation.setOnClickListener {
            getMap(catalog)
        }
    }

    private fun showPriceCart(catalog: Catalog){

        val priceButton = getString(R.string.show_price_add_cart_button) + catalog.price.toIndonesianFormat()
        binding.layoutCounterCart.addCart.text = priceButton

        binding.layoutCounterCart.counterAddCart.text = qytCart.toString()
        price = catalog.price

        binding.layoutCounterCart.addCartPlus.setOnClickListener {

            qytCart += 1

            binding.layoutCounterCart.counterAddCart.text = qytCart.toString()
            binding.layoutCounterCart.addCart.text = getString(R.string.show_price_add_cart_button)+totalPrice(price,qytCart.toDouble()).toIndonesianFormat()

            binding.layoutCounterCart.addCartMinus.isEnabled = qytCart > 1
        }

        binding.layoutCounterCart.addCartMinus.setOnClickListener {

            qytCart -= 1

            binding.layoutCounterCart.counterAddCart.text = qytCart.toString()
            binding.layoutCounterCart.addCart.text = getString(R.string.show_price_add_cart_button)+totalPrice(price,qytCart.toDouble()).toIndonesianFormat()

            binding.layoutCounterCart.addCartMinus.isEnabled = qytCart > 1
        }
    }

    private fun totalPrice(price: Double, qytCart: Double) = price * qytCart

    private fun getData() {
        intent.extras?.getParcelable<Catalog>(EXTRAS_ITEM)?.let {
            try {
                showDataDetail(it)
                showPriceCart(it)
            } catch (e: NullPointerException){
                Toast.makeText(this, getString(R.string.error_data), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getBackActivityDetail(){
        binding.layoutIvProduct.backDetail.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getMap(catalog: Catalog){
        startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(catalog.mapUrl)))
    }

    companion object{
        const val EXTRAS_ITEM = "EXTRAS_ITEM"
    }
}