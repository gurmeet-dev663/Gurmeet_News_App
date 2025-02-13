package com.gurmeet.alllanguagenewsapp.data.api

import com.gurmeet.alllanguagenewsapp.data.model.language.LanguageResponse
import com.gurmeet.alllanguagenewsapp.data.model.headlines.HeadlineResponse
import com.gurmeet.alllanguagenewsapp.utils.AppConstant.API_KEY
import com.gurmeet.alllanguagesapp.SourcesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface NetworkService {

    @Headers("X-Api-Key: $API_KEY")
    @GET("everything")
    suspend fun getNews(@Query("q") query: String): HeadlineResponse
    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String, @Query("page") page: Int,
        @Query("pageSize") pageSize: Int): HeadlineResponse

    @GET("top-headlines/sources")
    suspend fun getTopHeadlinesSources(@Query("apiKey") apiKey: String): SourcesResponse

    @GET("top-headlines")
    suspend fun getTopHeadlinesDetails(@Query("sources")sourceDetails:String,@Query("apiKey") apiKey: String,@Query("page") page: Int,
                                       @Query("pageSize") pageSize: Int): HeadlineResponse
    @GET("everything")
    suspend fun getTeslaArticles(@Query("q")q:String,@Query("sortBy")sorted:String,@Query("apiKey") apiKey: String,
                                 @Query("page") page: Int,
                                 @Query("pageSize") pageSize: Int): HeadlineResponse


    @GET("latest-news")
    suspend fun getAllLanguageResponse(@Query("language")language:String,@Query("apiKey") apiKey: String): LanguageResponse
    @GET("latest-news")
    suspend fun getAllLanguageResponseCountry(@Query("country")language:String,@Query("apiKey") apiKey: String): LanguageResponse

}