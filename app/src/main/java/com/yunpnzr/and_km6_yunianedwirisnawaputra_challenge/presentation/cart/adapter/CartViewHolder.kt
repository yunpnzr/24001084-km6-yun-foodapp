package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.base.ViewHolderBinder
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Cart
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.ItemCartMenuBinding
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.common.CartListener
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.doneEditing
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.toIndonesianFormat

class CartViewHolder(
    private val binding: ItemCartMenuBinding,
    private val cartListener: CartListener?
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
        setClickListeners(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            binding.ivCartMenu.load(item.menuImageUrl) {
                crossfade(true)
            }
            tvCounterAddCartMenu.text = item.itemQuantity.toString()
            tvCartMenu.text = item.menuName
            tvPriceCartMenu.text = (item.itemQuantity * item.menuPrice).toIndonesianFormat().toString()
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.etNotes.setText(item.itemNotes)
        binding.etNotes.doneEditing {
            binding.etNotes.clearFocus()
            val newItem = item.copy().apply {
                itemNotes = binding.etNotes.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setClickListeners(item: Cart) {
        with(binding) {
            btnAddCartMenuMinus.setOnClickListener { cartListener?.onMinusTotalItemCartClicked(item) }
            btnAddCartMenuPlus.setOnClickListener { cartListener?.onPlusTotalItemCartClicked(item) }
            ivDeleteCartMenu.setOnClickListener { cartListener?.onRemoveCartClicked(item) }
        }
    }
}