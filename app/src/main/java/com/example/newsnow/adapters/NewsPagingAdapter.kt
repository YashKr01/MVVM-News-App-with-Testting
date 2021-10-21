package com.example.newsnow.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.newsnow.databinding.ItemNewsBinding
import com.example.newsnow.network.model.NewsArticleDto

class NewsPagingAdapter(
    private val onItemClick: (NewsArticleDto) -> Unit,
    private val onBookmarkClick: (NewsArticleDto) -> Unit
) : PagingDataAdapter<NewsArticleDto, NewsViewHolder>(ItemComparator()) {

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

