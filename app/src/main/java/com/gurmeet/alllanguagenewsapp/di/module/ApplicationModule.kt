package com.gurmeet.alllanguagenewsapp.di.module

import android.content.Context
import com.gurmeet.alllanguagenewsapp.AllLanguageApplication
import com.gurmeet.alllanguagenewsapp.data.ApplicationContext
import com.gurmeet.alllanguagenewsapp.data.BaseUrl
import com.gurmeet.alllanguagenewsapp.data.api.NetworkService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AllLanguageApplication) {

    @ApplicationContext
    @Provides
    fun provideContext():Context{
        return application
    }

    @BaseUrl
    @Provides
    fun provideBaseUrl():String="https://newsapi.org/v2/"

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()


    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)
    }
}