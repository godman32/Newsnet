package com.gm.newsnet.news.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gm.newsnet.databinding.ItemCategoryBinding
import com.gm.newsnet.utills.listener.OnCategoryListener

/**
 * Created by @godman on 04/07/23.
 */

class CategoryAdapter(
    private val genres: List<String>,
    private val onCategoryListener: OnCategoryListener
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.category.text = genres[position]

        holder.binding.category.setOnClickListener {
            onCategoryListener.onCategorySelected(genres[position])
        }
    }

    override fun getItemCount(): Int {
        return genres.size
    }
}