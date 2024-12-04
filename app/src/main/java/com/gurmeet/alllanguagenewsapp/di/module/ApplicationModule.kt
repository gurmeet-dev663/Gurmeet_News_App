package com.gurmeet.alllanguagenewsapp.di.module

import android.content.Context
import com.gurmeet.alllanguagenewsapp.Application
import com.gurmeet.alllanguagenewsapp.data.ApplicationContext
import com.gurmeet.alllanguagenewsapp.data.BaseUrl
import com.gurmeet.alllanguagenewsapp.data.BaseUrl2
import com.gurmeet.alllanguagenewsapp.data.api.NetworkService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class ApplicationModule(private val application: Application) {

    @ApplicationContext
    @Provides
    fun provideContext():Context{
        return application
    }

    @BaseUrl
    @Provides
    fun provideBaseUrl():String="https://newsapi.org/v2/"

    @BaseUrl2
    @Provides
    fun provideBaseUrl2():String="https://api.currentsapi.services/v1/"



    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY  // Logs request and response bodies
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)  // Add the logging interceptor to OkHttpClient
            .build()
    }


   @Singleton
    @Provides
   fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)
    }
   @BaseUrl2
    @Singleton
    @Provides
    fun provideNetworkService2(
        @BaseUrl2 baseUrl: String,
        gsonConverterFactory: GsonConverterFactory
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)
    }



    /*   @BaseUrl
       @Provides
       fun provideRetrofit1(@BaseUrl baseUrl: String?, client: OkHttpClient?): Retrofit? {
           return Retrofit.Builder()
               .baseUrl(baseUrl)
               .client(client)
               .addConverterFactory(GsonConverterFactory.create())
               .build()
       }

       // Provide Retrofit instance for BaseUrl2
       @BaseUrl2
       @Provides
       fun provideRetrofit2(@BaseUrl2 baseUrl: String?, client: OkHttpClient?): Retrofit? {
           return Retrofit.Builder()
               .baseUrl(baseUrl)
               .client(client)
               .addConverterFactory(GsonConverterFactory.create())
               .build()
       }*/
}