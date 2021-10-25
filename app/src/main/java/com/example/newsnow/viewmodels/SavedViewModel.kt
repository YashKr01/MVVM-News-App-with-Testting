package com.example.newsnow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsnow.database.NewsArticle
import com.example.newsnow.database.NewsArticleDao
import com.example.newsnow.network.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    fun deleteArticle(article: NewsArticle) = viewModelScope.launch {
        repository.deleteArticle(article)
    }

    fun getNewsList() = repository.getSavedNews()

}