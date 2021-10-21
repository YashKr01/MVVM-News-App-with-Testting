package com.example.newsnow.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.newsnow.database.NewsArticle
import com.example.newsnow.network.model.NewsArticleDto

class ItemComparator : DiffUtil.ItemCallback<NewsArticleDto>() {

    override fun areItemsTheSame(oldItem: NewsArticleDto, newItem: NewsArticleDto): Boolean =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: NewsArticleDto, newItem: NewsArticleDto): Boolean =
        oldItem == newItem

}