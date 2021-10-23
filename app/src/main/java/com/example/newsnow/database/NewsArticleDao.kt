package com.example.newsnow.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsArticleDao {

    @Query("SELECT * FROM news_articles")
    fun getList(): Flow<List<NewsArticle>>

    @Delete
    suspend fun deleteArticle(newsArticle: NewsArticle)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(newsArticle: NewsArticle)

}