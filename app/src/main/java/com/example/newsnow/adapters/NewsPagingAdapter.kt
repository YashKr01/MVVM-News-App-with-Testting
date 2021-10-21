package com.example.newsnow.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.newsnow.database.NewsArticle
import com.example.newsnow.databinding.ItemNewsBinding

class NewsPagingAdapter(
    private val onItemClick: (NewsArticle) -> Unit,
    private val onBookmarkClick: (NewsArticle) -> Unit
) : PagingDataAdapter<NewsArticle, NewsViewHolder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClick = { position ->
                val article = getItem(position)
                if (article != null) onItemClick(article)
            },
            onBookmarkClick = { position ->
                val article = getItem(position)
                if (article != null) onBookmarkClick(article)
            }
        )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) holder.bind(currentItem)
    }

}

