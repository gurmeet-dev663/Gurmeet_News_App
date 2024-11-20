package com.gurmeet.alllanguagenewsapp.di.Component

import android.content.Context
import com.gurmeet.alllanguagenewsapp.Application
import com.gurmeet.alllanguagenewsapp.data.ApplicationContext
import com.gurmeet.alllanguagenewsapp.data.BaseUrl2
import com.gurmeet.alllanguagenewsapp.data.api.NetworkService
import com.gurmeet.alllanguagenewsapp.data.repository.LanguageRepository
import com.gurmeet.alllanguagenewsapp.data.repository.CountryRepository
import com.gurmeet.alllanguagenewsapp.data.repository.TopHeadlineRepository
import com.gurmeet.alllanguagenewsapp.data.repository.NewsSourceRepository
import com.gurmeet.alllanguagenewsapp.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: Application)

    @ApplicationContext
    fun getContext(): Context


    fun getNetworkService(): NetworkService

    @BaseUrl2
    fun getNewtorkService2():NetworkService



    fun getTopHeadlineRepository(): TopHeadlineRepository

    fun getTopSourceRepository():NewsSourceRepository
    fun getSearchRepository():CountryRepository
    fun getLanguageRepostory(): LanguageRepository



}