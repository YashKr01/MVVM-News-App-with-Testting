package com.example.newsnow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsnow.database.NewsArticleDao
import com.example.newsnow.network.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val dao: NewsArticleDao
) : ViewModel() {

    val newsList = repository.getTopHeadlines().cachedIn(viewModelScope)

}