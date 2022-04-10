package com.example.newsnow.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsnow.data.database.NewsArticle
import com.example.newsnow.data.database.NewsArticleDao
import com.example.newsnow.data.network.retrofit.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val dao: NewsArticleDao
) : ViewModel() {

    init {
        getCurrentQuery()
    }

    private val _currentQuery = MutableLiveData<String>()
    val query get() = _currentQuery

    fun insertArticle(newsArticle: NewsArticle) = viewModelScope.launch {
        repository.insertArticle(newsArticle)
    }

    fun deleteArticle(newsArticle: NewsArticle) = viewModelScope.launch {
        repository.deleteArticle(newsArticle)
    }

    val newsList = repository.getTopHeadlines().cachedIn(viewModelScope)

    private fun getCurrentQuery() = viewModelScope.launch {
        repository.getCurrentQuery().collectLatest {
            _currentQuery.postValue(it)
        }
    }

    fun setCurrentQuery(query: String) = viewModelScope.launch { repository.setCurrentQuery(query) }

    fun getDatabaseList() = dao.getList()

}