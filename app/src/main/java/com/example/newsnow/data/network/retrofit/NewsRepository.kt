package com.example.newsnow.data.network.retrofit

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.newsnow.data.database.NewsArticle
import com.example.newsnow.data.database.NewsArticleDao
import com.example.newsnow.data.datastore.PreferenceStorage
import com.example.newsnow.data.network.paging.NewsPagingSource
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val dao: NewsArticleDao,
    private val preferenceStorage: PreferenceStorage
) {

    fun getTopHeadlines(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false,
                initialLoadSize = 30
            ),
            pagingSourceFactory = { NewsPagingSource(api = apiInterface, dao = dao, query = query) }
        ).flow

    suspend fun insertArticle(newsArticle: NewsArticle) = dao.insertArticle(newsArticle)

    suspend fun deleteArticle(newsArticle: NewsArticle) = dao.deleteArticle(newsArticle)

    fun getSavedNews() = dao.getList()

    suspend fun deleteAllNews() = dao.deleteAllNews()

    val currentQuery = preferenceStorage.currentQuery

    suspend fun setCurrentQuery(query: String) = preferenceStorage.setCurrentQuery(query)

}