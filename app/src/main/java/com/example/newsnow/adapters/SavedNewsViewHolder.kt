package com.example.newsnow.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsnow.database.NewsArticle
import com.example.newsnow.databinding.ItemSavedNewsBinding
import com.example.newsnow.utils.ExtensionFunctions.trimDate

class SavedNewsViewHolder(
    private val binding: ItemSavedNewsBinding,
    private val onItemClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.itemSavedNewsDelete.setOnClickListener {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) onDeleteClick(position)
        }

        binding.root.setOnClickListener {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) onItemClick(position)
        }
    }

    fun bind(article: NewsArticle){
        binding.apply {

            Glide.with(itemView)
                .load(article.urlToImage)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.itemSavedNewsImage)

            itemSavedNewsTitle.text = article.title
            itemSavedNewsDescription.text = article.description
            itemSavedNewsDate.text = article.publishedAt.trimDate()

        }
    }

}