package com.softlabio.newsapi.api

import com.softlabio.newsapi.constants.AppConstants.Companion.API_KEY
import com.softlabio.newsapi.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getBrakingNews(
        @Query(value = "country")
        countryCode: String = "us",

        @Query(value = "page")
        pageNumber:Int = 2,

        @Query(value = "apiKey")
        apiKey:String = API_KEY
    ):Response<NewsResponse>


    // v2/everything?q=Apple&from=2021-09-19&sortBy=popularity&apiKey=API_KEY
    @GET("v2/everything")
    suspend fun searchNews(
        @Query(value = "q")
        searchQ: String,

        @Query(value = "page")
        pageNumber:Int = 2,

        @Query(value = "apiKey")
        apiKey:String = API_KEY
    ):Response<NewsResponse>
}