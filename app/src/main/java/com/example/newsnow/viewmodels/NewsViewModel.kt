package com.example.newsnow.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.newsnow.data.database.NewsArticle
import com.example.newsnow.data.database.NewsArticleDao
import com.example.newsnow.data.network.paging.NewsPagingSource
import com.example.newsnow.data.network.retrofit.ApiInterface
import com.example.newsnow.data.network.retrofit.NewsRepository
import com.example.newsnow.utils.Constants.SPORTS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val dao: NewsArticleDao,
    private val apiInterface: ApiInterface
) : ViewModel() {

    private val currentQuery = MutableLiveData(SPORTS)

    fun changeQuery(newQuery: String)  {
        currentQuery.value = newQuery
    }

    // News List
    val newsList = currentQuery.switchMap {
        repository.getTopHeadlines(it).cachedIn(viewModelScope)
    }

    //  database Operations
    fun insertArticle(newsArticle: NewsArticle) = viewModelScope.launch {
        repository.insertArticle(newsArticle)
    }

    fun deleteArticle(newsArticle: NewsArticle) = viewModelScope.launch {
        repository.deleteArticle(newsArticle)
    }

}