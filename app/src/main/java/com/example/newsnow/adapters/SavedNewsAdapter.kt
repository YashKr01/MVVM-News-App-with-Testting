package com.example.newsnow.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.newsnow.database.NewsArticle
import com.example.newsnow.databinding.ItemSavedNewsBinding

class SavedNewsAdapter(
    private val onItemClick: (NewsArticle) -> Unit,
    private val onDeleteClick: (NewsArticle) -> Unit
) : ListAdapter<NewsArticle, SavedNewsViewHolder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNewsViewHolder =
        SavedNewsViewHolder(
            ItemSavedNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ),
            onItemClick = { position ->
                val article = getItem(position)
                if (article != null) onItemClick(article)
            },
            onDeleteClick = { position ->
                val article = getItem(position)
                if (article != null) onDeleteClick(article)
            }
        )

    override fun onBindViewHolder(holder: SavedNewsViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) holder.bind(currentItem)
    }

}