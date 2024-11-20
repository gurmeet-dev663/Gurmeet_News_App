package com.gurmeet.alllanguagenewsapp

import android.app.Application
import com.gurmeet.alllanguagenewsapp.di.Component.ApplicationComponent
import com.gurmeet.alllanguagenewsapp.di.Component.DaggerApplicationComponent
import com.gurmeet.alllanguagenewsapp.di.module.ApplicationModule

class Application : Application() {
    lateinit var applicationComponent :ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }



    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

}