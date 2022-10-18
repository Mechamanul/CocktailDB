package com.mechamanul.cocktaildb.ui.elements.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mechamanul.cocktaildb.databinding.CategoriesItemBinding

class CategoriesAdapter(categories: List<String>) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    private var _categories = categories

    inner class ViewHolder(val binding: CategoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String) {
            binding.apply {
                categoryName.text = name
            }

        }
    }

    fun submitList(categories: List<String>) {
        _categories = categories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CategoriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(_categories[position])
    }

    override fun getItemCount(): Int {
        return _categories.size
    }
}