package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.checkout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.PriceItem
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.ItemPriceCheckoutBinding
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.toIndonesianFormat

class PriceListAdapter(private val itemClick: (PriceItem) -> Unit) :
    RecyclerView.Adapter<PriceListAdapter.PriceItemViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<PriceItem>() {
                override fun areItemsTheSame(
                    oldItem: PriceItem,
                    newItem: PriceItem,
                ): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areContentsTheSame(
                    oldItem: PriceItem,
                    newItem: PriceItem,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<PriceItem>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PriceItemViewHolder {
        val binding = ItemPriceCheckoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PriceItemViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: PriceItemViewHolder,
        position: Int,
    ) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    class PriceItemViewHolder(
        private val binding: ItemPriceCheckoutBinding,
        val itemClick: (PriceItem) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: PriceItem) {
            with(item) {
                binding.tvPriceSummary.text = item.total.toIndonesianFormat()
                binding.tvItemSummary.text = item.name
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
