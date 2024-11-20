package com.gurmeet.alllanguagenewsapp.di.Component

import com.gurmeet.alllanguagenewsapp.data.ActivityScope
import com.gurmeet.alllanguagenewsapp.di.module.ActivityModule
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.SearchActivity
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.countries.CountryActivity
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.fetchnews.FetchNewsActivity
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.headlines.HeadLineActivity
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.newsources.NewsSourceActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
//@Component(modules = [ViewModelModule::class, AndroidSupportInjectionModule::class])
interface ActivityComponent {

   fun inject(activity: HeadLineActivity)
    fun inject(activity:NewsSourceActivity)
    fun inject(activity:SearchActivity)
 fun inject(activity: CountryActivity)
 fun inject(activity:FetchNewsActivity)

}