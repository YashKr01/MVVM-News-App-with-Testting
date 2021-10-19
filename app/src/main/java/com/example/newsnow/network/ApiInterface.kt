package com.example.newsnow.network

import com.example.newsnow.network.model.NewsResponse
import com.example.newsnow.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("top-headlines?country=us&pageSize=100&apiKey=$API_KEY")
    suspend fun getBreakingNews(@Query("apiKey") apiKey: String): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String
    ): NewsResponse

}