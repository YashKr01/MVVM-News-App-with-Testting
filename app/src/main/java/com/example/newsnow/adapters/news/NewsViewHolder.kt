package com.example.newsnow.adapters.news

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsnow.R
import com.example.newsnow.data.database.NewsArticle
import com.example.newsnow.databinding.ItemNewsBinding
import com.example.newsnow.utils.ExtensionFunctions.trimDate

class NewsViewHolder(
    private val binding: ItemNewsBinding,
    private val onItemClick: (Int) -> Unit,
    private val onBookmarkClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.apply {

            root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) onItemClick(position)
            }

        }
    }

    fun bind(article: NewsArticle) {
        binding.apply {

            itemNewsBookmark.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {

                    onBookmarkClick(position)
                    if(article.isBookmarked) {
                        itemNewsBookmark.setImageResource(R.drawable.ic_bookmark_hollow)
                        article.isBookmarked = !article.isBookmarked
                    }
                    else{
                        itemNewsBookmark.setImageResource(R.drawable.ic_bookmark_filled)
                        article.isBookmarked = !article.isBookmarked
                    }
                }
            }

            Glide.with(itemView)
                .load(article.urlToImage)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.itemNewsImage)

            itemNewsTitle.text = article.title
            itemNewsDescription.text = article.description
            itemNewsDate.text = article.publishedAt.trimDate()

            itemNewsBookmark.setImageResource(
                when {
                    article.isBookmarked -> R.drawable.ic_bookmark_filled
                    else -> R.drawable.ic_bookmark_hollow
                }
            )

        }
    }

}