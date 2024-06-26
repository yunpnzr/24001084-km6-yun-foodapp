package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.checkout

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.R
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.ActivityCheckoutBinding
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.DialogCheckoutBinding
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.auth.login.LoginActivity
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.checkout.adapter.PriceListAdapter
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.common.adapter.CartListAdapter
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.proceedWhen
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.toIndonesianFormat
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutActivity : AppCompatActivity() {
    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val checkoutViewModel: CheckoutViewModel by viewModel()

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }

    private val priceItemAdapter: PriceListAdapter by lazy {
        PriceListAdapter {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListeners()
        setupList()
        observeData()
    }

    private fun setClickListeners() {
        binding.layoutTopBarCheckout.ivBackCheckout.setOnClickListener {
            onBackPressed()
        }
        binding.btnCheckout.setOnClickListener {
            if (checkoutViewModel.isLoggedIn()) {
                /*viewModel.deleteAllCart()
                showDialog()*/
                observeCheckout()
            } else {
                navigateToLogin()
            }
        }
    }

    private fun observeCheckout() {
        checkoutViewModel.checkoutCart().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    checkoutViewModel.deleteAllCart()
                    showDialog()
                },
                doOnError = {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                },
            )
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun showDialog() {
        val dialogBinding = DialogCheckoutBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        dialogBinding.btnConfirm.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        dialog.show()
    }

    private fun setupList() {
        binding.rvCart.adapter = adapter
        binding.rvShoppingSummary.adapter = priceItemAdapter
    }

    private fun observeData() {
        checkoutViewModel.checkoutData.observe(this) { result ->
            result.proceedWhen(doOnSuccess = {
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvErrorCart.isVisible = false
                binding.rvCart.isVisible = true
                binding.cvButtonCheckout.isVisible = true
                binding.btnCheckout.isEnabled = true
                binding.layoutTopBarCheckout.root.isVisible = true
                binding.cvDeliveryMethod.isVisible = true
                binding.cvPayMethod.isVisible = true
                binding.cvSummary.isVisible = true
                result.payload?.let { (carts, priceItems, totalPrice) ->
                    adapter.submitData(carts)
                    binding.tvTotalPriceCheckout.text = totalPrice.toIndonesianFormat()
                    priceItemAdapter.submitData(priceItems)
                }
            }, doOnLoading = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = true
                binding.layoutState.tvErrorCart.isVisible = false
                binding.rvCart.isVisible = false
                binding.cvButtonCheckout.isVisible = true
                binding.btnCheckout.isEnabled = false
                binding.layoutTopBarCheckout.root.isVisible = true
                binding.cvDeliveryMethod.isVisible = false
                binding.cvPayMethod.isVisible = false
                binding.cvSummary.isVisible = false
            }, doOnError = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvErrorCart.isVisible = true
                binding.layoutState.tvErrorCart.text = result.exception?.message.orEmpty()
                binding.rvCart.isVisible = false
                binding.cvButtonCheckout.isVisible = true
                binding.btnCheckout.isEnabled = false
                binding.layoutTopBarCheckout.root.isVisible = true
                binding.cvDeliveryMethod.isVisible = false
                binding.cvPayMethod.isVisible = false
                binding.cvSummary.isVisible = false
            }, doOnEmpty = { data ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvErrorCart.isVisible = true
                binding.layoutState.tvErrorCart.text = getString(R.string.empty_cart)
                data.payload?.let { (_, _, totalPrice) ->
                    binding.tvTotalPriceCheckout.text = totalPrice.toIndonesianFormat()
                }
                binding.rvCart.isVisible = false
                binding.cvButtonCheckout.isVisible = true
                binding.btnCheckout.isEnabled = false
                binding.layoutTopBarCheckout.root.isVisible = true
                binding.cvDeliveryMethod.isVisible = false
                binding.cvPayMethod.isVisible = false
                binding.cvSummary.isVisible = false
            })
        }
    }
}
