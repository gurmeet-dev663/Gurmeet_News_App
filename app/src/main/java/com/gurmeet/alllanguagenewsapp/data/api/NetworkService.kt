package com.gurmeet.alllanguagenewsapp.data.api

import androidx.annotation.Keep
import com.gurmeet.alllanguagenewsapp.data.BaseUrl2
import com.gurmeet.alllanguagenewsapp.data.model.model.language.AllLanguageResponse
import com.gurmeet.alllanguagenewsapp.data.model.model.topheadlines.TopHeadLinesResponse
import com.gurmeet.alllanguagenewsapp.utils.AppConstant.API_KEY
import com.gurmeet.alllanguagesapp.TopSourcesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface NetworkService {

    @Headers("X-Api-Key: $API_KEY")
    @GET("everything")
    suspend fun getNews(@Query("q") query: String): TopHeadLinesResponse
    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String): TopHeadLinesResponse

    @GET("top-headlines/sources")
    suspend fun getTopHeadlinesSources(@Query("apiKey") apiKey: String): TopSourcesResponse

    @GET("top-headlines")
    suspend fun getTopHeadlinesDetails(@Query("sources")sourceDetails:String,@Query("apiKey") apiKey: String): TopHeadLinesResponse
    @GET("everything")
    suspend fun getTeslaArticles(@Query("q")q:String,@Query("sortBy")sorted:String,@Query("apiKey") apiKey: String): TopHeadLinesResponse


    @GET("latest-news")
    suspend fun getAllLanguageResponse(@Query("language")language:String,@Query("apiKey") apiKey: String): AllLanguageResponse
    @GET("latest-news")
    suspend fun getAllLanguageResponseCountry(@Query("country")language:String,@Query("apiKey") apiKey: String): AllLanguageResponse

}