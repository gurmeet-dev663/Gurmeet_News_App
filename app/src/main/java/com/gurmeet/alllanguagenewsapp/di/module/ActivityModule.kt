package com.gurmeet.alllanguagenewsapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gurmeet.alllanguagenewsapp.data.ActivityContext
import com.gurmeet.alllanguagenewsapp.data.repository.AllLanguageRepository
import com.gurmeet.alllanguagenewsapp.data.repository.SearchRepository
import com.gurmeet.alllanguagenewsapp.data.repository.TopHeadlineRepository
import com.gurmeet.alllanguagenewsapp.data.repository.TopSourceRepository
import com.gurmeet.alllanguagenewsapp.ui.base.ViewModelProviderFactory
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.countries.CountryAdapter
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.countries.CountryViewModel
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.language.TopLanguageHeadlineAdapter
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.language.TopLanguageViewModel
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.topheadlines.TopHeadLineAdapter
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.topheadlines.TopHeadLineViewModel
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.topsources.TopSourceAdapter
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.topsources.TopSourcesViewModel

import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity)  {
    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }
    @Provides
    fun provideNewsListViewModel(topHeadlineRepository: TopHeadlineRepository): TopHeadLineViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(TopHeadLineViewModel::class) {
                TopHeadLineViewModel(topHeadlineRepository)
            })[TopHeadLineViewModel::class.java]
    }
    @Provides
    fun provideTopSourceViewModel(topSourceRepository: TopSourceRepository): TopSourcesViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(TopSourcesViewModel::class) {
                TopSourcesViewModel(topSourceRepository)
            })[TopSourcesViewModel::class.java]
    }
    @Provides
    fun provideCountryViewModel(searchRepository: SearchRepository): CountryViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(CountryViewModel::class) {
                CountryViewModel(searchRepository)
            })[CountryViewModel::class.java]
    }
    @Provides
    fun provideAllLanugage(allLanguageRepository: AllLanguageRepository): TopLanguageViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(TopLanguageViewModel::class) {
                TopLanguageViewModel(allLanguageRepository)
            })[TopLanguageViewModel::class.java]
    }




    @Provides
    fun provideTopHeadlineAdapter() = TopHeadLineAdapter(ArrayList())
  @Provides
    fun provideTopSourceAdapter()=TopSourceAdapter(ArrayList())

    @Provides
    fun provideCountryAdapter()=CountryAdapter(ArrayList())

    @Provides
    fun allLanguageAdapter()=TopLanguageHeadlineAdapter(ArrayList())

}