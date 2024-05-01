package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.base.OnItemClickedListener
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.base.ViewHolderBinder
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Catalog
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.ItemGridCatalogMenuBinding
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.ItemListCatalogMenuBinding

class CatalogMenuAdapter(
    private val listener: OnItemClickedListener<Catalog>,
    private val listMode: Int = LIST_MODE,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val GRID_MODE = 0
        const val LIST_MODE = 1
    }

    private var asyncDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Catalog>() {
                override fun areItemsTheSame(
                    oldItem: Catalog,
                    newItem: Catalog,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Catalog,
                    newItem: Catalog,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<Catalog>) {
        asyncDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return if (listMode == GRID_MODE) {
            CatalogGridViewHolder(
                ItemGridCatalogMenuBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                listener,
            )
        } else {
            CatalogListViewHolder(
                ItemListCatalogMenuBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                listener,
            )
        }
    }

    override fun getItemCount(): Int = asyncDiffer.currentList.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        if (holder !is ViewHolderBinder<*>) return
        (holder as ViewHolderBinder<Catalog>).bind(asyncDiffer.currentList[position])
    }
}
