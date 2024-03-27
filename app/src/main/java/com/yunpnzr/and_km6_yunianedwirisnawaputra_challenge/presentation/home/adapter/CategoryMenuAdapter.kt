package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.R
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Category
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.ItemCategoryMenuBinding

class CategoryMenuAdapter: RecyclerView.Adapter<CategoryMenuAdapter.CategoryViewHolder>() {

    private val data = mutableListOf<Category>()

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    fun submitDataCategory(items: List<Category>){
        data.addAll(items)
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(
        private val binding: ItemCategoryMenuBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Category){
            binding.ivCategoryMenu.load(item.imageUrl){
                placeholder(R.drawable.img_loading_picture)
                error(R.drawable.img_error)
            }
            binding.tvCategoryMenu.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoryMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(data[position])
    }
}