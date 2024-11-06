package com.gurmeet.alllanguagenewsapp.di.Component

import com.gurmeet.alllanguagenewsapp.data.ActivityScope
import com.gurmeet.alllanguagenewsapp.di.module.ActivityModule
import com.gurmeet.alllanguagenewsapp.ui.topheadlines.TopHeadLineActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: TopHeadLineActivity)

}