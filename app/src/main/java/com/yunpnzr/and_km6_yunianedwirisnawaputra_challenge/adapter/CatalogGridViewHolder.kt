package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.R
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.base.OnItemClickedListener
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.base.ViewHolderBinder
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Catalog
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.ItemGridCatalogMenuBinding
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.toIndonesianFormat

class CatalogGridViewHolder(
    private val binding: ItemGridCatalogMenuBinding,
    private val listener: OnItemClickedListener<Catalog>
): RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Catalog> {
    override fun bind(item: Catalog) {
        item.let {
            binding.ivCatalogMenu.load(it.imageUrl){
                placeholder(R.drawable.img_loading_picture)
                error(R.drawable.img_error)
            }
            binding.tvCatalogName.text = it.name
            binding.tvCatalogPrice.text = it.price.toIndonesianFormat()
            itemView.setOnClickListener{
                listener.onClicked(item)
            }
        }
    }
}