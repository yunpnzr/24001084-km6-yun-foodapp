package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.base.ViewHolderBinder
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Cart
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.ItemCheckoutMenuBinding

class CartOrderViewHolder(
    private val binding: ItemCheckoutMenuBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            binding.ivCheckout.load(item.menuImageUrl) {
                crossfade(true)
            }
            tvQytCheckout.text = item.itemQuantity.toString()
            tvNameMenuCheckout.text = item.menuName
            tvPriceCartMenu.text = (item.itemQuantity * item.menuPrice).toString()
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.tvNotesCheckout.text = item.itemNotes
    }

}