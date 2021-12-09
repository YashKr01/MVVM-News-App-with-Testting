package com.example.newsnow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsnow.data.database.NewsArticle
import com.example.newsnow.data.network.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    fun insertArticle(newsArticle: NewsArticle) = viewModelScope.launch {
        repository.insertArticle(newsArticle)
    }

    fun deleteArticle(newsArticle: NewsArticle) = viewModelScope.launch {
        repository.deleteArticle(newsArticle)
    }

    val newsList = repository.getTopHeadlines().cachedIn(viewModelScope)

}