package com.gurmeet.alllanguagenewsapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.gurmeet.alllanguagenewsapp.data.ActivityContext
import com.gurmeet.alllanguagenewsapp.data.repository.TopHeadlineRepository
import com.gurmeet.alllanguagenewsapp.ui.base.ViewModelProviderFactory
import com.gurmeet.alllanguagenewsapp.ui.topheadlines.TopHeadLineAdapter
import com.gurmeet.alllanguagenewsapp.ui.topheadlines.TopHeadLineViewModel

import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {
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
    fun provideTopHeadlineAdapter() = TopHeadLineAdapter(ArrayList())
}