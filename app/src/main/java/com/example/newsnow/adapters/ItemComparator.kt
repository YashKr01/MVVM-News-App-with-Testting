package com.example.newsnow.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.newsnow.database.NewsArticle

class ItemComparator : DiffUtil.ItemCallback<NewsArticle>() {

    override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean =
        oldItem == newItem

}