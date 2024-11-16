package com.gurmeet.alllanguagenewsapp.di.Component

import android.content.Context
import com.gurmeet.alllanguagenewsapp.AllLanguageApplication
import com.gurmeet.alllanguagenewsapp.data.ApplicationContext
import com.gurmeet.alllanguagenewsapp.data.BaseUrl
import com.gurmeet.alllanguagenewsapp.data.BaseUrl2
import com.gurmeet.alllanguagenewsapp.data.api.NetworkService
import com.gurmeet.alllanguagenewsapp.data.repository.AllLanguageRepository
import com.gurmeet.alllanguagenewsapp.data.repository.SearchRepository
import com.gurmeet.alllanguagenewsapp.data.repository.TopHeadlineRepository
import com.gurmeet.alllanguagenewsapp.data.repository.TopSourceRepository
import com.gurmeet.alllanguagenewsapp.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: AllLanguageApplication)

    @ApplicationContext
    fun getContext(): Context


    fun getNetworkService(): NetworkService

    @BaseUrl2
    fun getNewtorkService2():NetworkService



    fun getTopHeadlineRepository(): TopHeadlineRepository

    fun getTopSourceRepository():TopSourceRepository
    fun getSearchRepository():SearchRepository
    fun getLanguageRepostory():AllLanguageRepository



}