package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.R
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.base.OnItemClickedListener
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.base.ViewHolderBinder
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Catalog
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.ItemListCatalogMenuBinding
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.toIndonesianFormat

class CatalogListViewHolder(
    private val binding: ItemListCatalogMenuBinding,
    private val listener: OnItemClickedListener<Catalog>
): RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Catalog> {
    override fun bind(item: Catalog) {
        item.let {
            binding.ivCatalogMenu.load(it.imageUrl){
                placeholder(R.drawable.img_loading_picture)
                error(R.drawable.img_error)
            }
            binding.tvCatalogName.text = it.name
            binding.tvPriceCatalog.text = it.price.toIndonesianFormat()
            itemView.setOnClickListener{
                listener.onClicked(item)
            }
        }
    }
}