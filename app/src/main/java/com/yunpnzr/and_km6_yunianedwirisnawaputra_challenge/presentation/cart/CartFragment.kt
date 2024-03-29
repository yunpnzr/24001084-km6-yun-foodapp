package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.R
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.cart.CartDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.cart.CartDatabaseDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Cart
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CartRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CartRepositoryImpl
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.local.database.AppDatabase
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.FragmentCartBinding
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.checkout.CheckoutActivity
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.common.CartListener
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.common.adapter.CartListAdapter
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.GenericViewModelFactory
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.hideKeyboard
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.proceedWhen
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.toIndonesianFormat

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private val viewModel: CartViewModel by viewModels {
        val databbase = AppDatabase.getDatabase(requireContext())
        val dataSource: CartDataSource = CartDatabaseDataSource(databbase.cartDao())
        val repository: CartRepository = CartRepositoryImpl(dataSource)
        GenericViewModelFactory.create(CartViewModel(repository))
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter(object : CartListener {
            override fun onPlusTotalItemCartClicked(cart: Cart) {
                viewModel.increaseCart(cart)
            }

            override fun onMinusTotalItemCartClicked(cart: Cart) {
                viewModel.decreaseCart(cart)
            }

            override fun onRemoveCartClicked(cart: Cart) {
                viewModel.removeCart(cart)
            }

            override fun onUserDoneEditingNotes(cart: Cart) {
                viewModel.setCartNote(cart)
                hideKeyboard()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observeData()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnAddCheckout.setOnClickListener {
            startActivity(Intent(requireContext(), CheckoutActivity::class.java))
        }
    }

    private fun observeData() {
        viewModel.getAllCarts().observe(viewLifecycleOwner){
            it.proceedWhen (
                doOnLoading = {
                    binding.layoutContentState.root.isVisible = true
                    binding.layoutContentState.pbLoading.isVisible = true
                    binding.layoutContentState.tvErrorCart.isVisible = false
                    binding.rvCart.isVisible = false
                    binding.btnAddCheckout.isEnabled = false
                },
                doOnSuccess = { success ->
                    binding.layoutContentState.root.isVisible = false
                    binding.layoutContentState.pbLoading.isVisible = false
                    binding.layoutContentState.tvErrorCart.isVisible = false
                    binding.rvCart.isVisible = true
                    binding.btnAddCheckout.isEnabled = true
                    success.payload?.let {(carts, totalPrice) ->
                        adapter.submitData(carts)
                        binding.tvPriceAddCheckout.text = totalPrice.toIndonesianFormat()
                    }
                },
                doOnError = { error ->
                    binding.layoutContentState.root.isVisible = true
                    binding.layoutContentState.pbLoading.isVisible = false
                    binding.layoutContentState.tvErrorCart.isVisible = true
                    binding.layoutContentState.tvErrorCart.text = error.exception?.message.toString()
                    binding.rvCart.isVisible = false
                    binding.btnAddCheckout.isEnabled = false
                },
                doOnEmpty = { empty ->
                    binding.layoutContentState.root.isVisible = true
                    binding.layoutContentState.pbLoading.isVisible = false
                    binding.layoutContentState.tvErrorCart.isVisible = true
                    binding.layoutContentState.tvErrorCart.text = getString(R.string.empty_cart)
                    binding.rvCart.isVisible = false
                    binding.btnAddCheckout.isEnabled = false
                    empty.payload?.let {(carts, totalPrice) ->
                        binding.tvPriceAddCheckout.text = totalPrice.toIndonesianFormat()
                    }
                }
            )
        }
    }


    private fun setupList() {
        binding.rvCart.adapter = this@CartFragment.adapter
    }
}