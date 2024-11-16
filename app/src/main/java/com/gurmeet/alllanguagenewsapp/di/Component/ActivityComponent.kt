package com.gurmeet.alllanguagenewsapp.di.Component

import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.gurmeet.alllanguagenewsapp.data.ActivityScope
import com.gurmeet.alllanguagenewsapp.data.repository.TopHeadlineRepository
import com.gurmeet.alllanguagenewsapp.di.module.ActivityModule
import com.gurmeet.alllanguagenewsapp.ui.base.BaseActivity
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.SearchActivity
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.countries.CountryActivity
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.language.TopLanguageSearchActivity
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.topheadlines.TopHeadLineActivity
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.topsources.TopSourceActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
//@Component(modules = [ViewModelModule::class, AndroidSupportInjectionModule::class])
interface ActivityComponent {

   fun inject(activity: TopHeadLineActivity)
    fun inject(activity:TopSourceActivity)
    fun inject(activity:SearchActivity)
 fun inject(activity: CountryActivity)
 fun inject(activity:TopLanguageSearchActivity)

}