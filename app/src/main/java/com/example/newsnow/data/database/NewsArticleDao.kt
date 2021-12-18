package com.example.newsnow.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsArticleDao {

    @Query("SELECT * FROM news_articles ORDER BY title ASC")
    fun getList(): Flow<List<NewsArticle>>

    @Delete
    suspend fun deleteArticle(newsArticle: NewsArticle)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(newsArticle: NewsArticle)

    @Query("DELETE FROM news_articles")
    suspend fun deleteAllNews()

}