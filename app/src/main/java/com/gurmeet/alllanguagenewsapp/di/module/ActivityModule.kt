package com.gurmeet.alllanguagenewsapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gurmeet.alllanguagenewsapp.data.ActivityContext
import com.gurmeet.alllanguagenewsapp.data.repository.LanguageRepository
import com.gurmeet.alllanguagenewsapp.data.repository.CountryRepository
import com.gurmeet.alllanguagenewsapp.data.repository.TopHeadlineRepository
import com.gurmeet.alllanguagenewsapp.data.repository.NewsSourceRepository
import com.gurmeet.alllanguagenewsapp.ui.base.ViewModelProviderFactory
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.countries.CountryAdapter
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.countries.CountryViewModel
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.fetchnews.FetchNewsAdapter
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.fetchnews.FetchNewsViewModel
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.headlines.HeadLineAdapter
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.headlines.HeadLineViewModel
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.newsources.NewsSourceAdapter
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.newsources.NewsSourcesViewModel

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
    fun provideNewsListViewModel(topHeadlineRepository: TopHeadlineRepository): HeadLineViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(HeadLineViewModel::class) {
                HeadLineViewModel(topHeadlineRepository)
            })[HeadLineViewModel::class.java]
    }
    @Provides
    fun provideTopSourceViewModel(topSourceRepository: NewsSourceRepository): NewsSourcesViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(NewsSourcesViewModel::class) {
                NewsSourcesViewModel(topSourceRepository)
            })[NewsSourcesViewModel::class.java]
    }
    @Provides
    fun provideCountryViewModel(searchRepository: CountryRepository): CountryViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(CountryViewModel::class) {
                CountryViewModel(searchRepository)
            })[CountryViewModel::class.java]
    }
    @Provides
    fun provideAllLanugage(allLanguageRepository: LanguageRepository): FetchNewsViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(FetchNewsViewModel::class) {
                FetchNewsViewModel(allLanguageRepository)
            })[FetchNewsViewModel::class.java]
    }




    @Provides
    fun provideTopHeadlineAdapter() = HeadLineAdapter(ArrayList())
  @Provides
    fun provideTopSourceAdapter()=NewsSourceAdapter(ArrayList())

    @Provides
    fun provideCountryAdapter()=CountryAdapter(ArrayList())

    @Provides
    fun allLanguageAdapter()=FetchNewsAdapter(ArrayList())

}