package com.example.newsnow.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsArticleDao {

    @Query("SELECT * FROM news_articles")
    fun getNewsList(): Flow<List<NewsArticle>>

    @Delete
    suspend fun deleteNewsArticle(newsArticle: NewsArticle)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewsArticle(newsArticle: NewsArticle)

}