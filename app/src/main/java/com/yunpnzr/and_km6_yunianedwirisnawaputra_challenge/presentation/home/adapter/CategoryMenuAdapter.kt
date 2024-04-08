package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.R
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Category
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.ItemCategoryMenuBinding

class CategoryMenuAdapter(
    private val itemClick : (Category)->Unit
): RecyclerView.Adapter<CategoryMenuAdapter.CategoryViewHolder>() {

    private val data = mutableListOf<Category>()

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitDataCategory(items: List<Category>){
        data.addAll(items)
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(
        private val binding: ItemCategoryMenuBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Category){
            with(item){
                binding.ivCategoryMenu.load(item.imageUrl){
                    placeholder(R.drawable.img_loading_picture)
                    error(R.drawable.img_error)
                }
                binding.tvCategoryMenu.text = item.name
                itemView.setOnClickListener {
                    itemClick(this)
                }
            }
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