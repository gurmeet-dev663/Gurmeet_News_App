package com.gurmeet.alllanguagenewsapp.ui.mainactivity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer

import com.gurmeet.alllanguagenewsapp.R
import com.gurmeet.alllanguagenewsapp.databinding.ActivityMainNewsAppBinding

import com.gurmeet.alllanguagenewsapp.ui.base.BaseActivity
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.countries.CountryActivity
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.headlines.HeadLineActivity
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.newsources.NewsSourceActivity

class MainActivityNewsApp : BaseActivity<ActivityMainNewsAppBinding,Nothing>(){
var isInternetPresent=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resetToastCounter()
        networkViewModel.isConnected.observe(this, Observer { isConnected ->
            if (isConnected) {
                isInternetPresent=true
            } else {
                isInternetPresent=false
                toastInternetNotAvailable(3)
            }
        })
        setupButtons()
    }
    override fun inflateBinding(inflater: LayoutInflater): ActivityMainNewsAppBinding {
        return ActivityMainNewsAppBinding.inflate(inflater)
    }

    override fun getViewModelClass(): Class<Nothing> {
        // Return Nothing if this activity does not require a ViewModel
        return Nothing::class.java
    }


    private fun setupButtons() {
        binding.topHeadLines.setOnClickListener { onButtonClick(binding.topHeadLines) }
        binding.newsSources.setOnClickListener { onButtonClick(binding.newsSources) }
        binding.countries.setOnClickListener { onButtonClick(binding.countries) }
        binding.languages.setOnClickListener { onButtonClick(binding.languages) }
        binding.search.setOnClickListener { onButtonClick(binding.search) }
        binding.tesla.setOnClickListener { onButtonClick(binding.tesla) }
        // List of buttons
        val buttons = listOf(binding.topHeadLines, binding.newsSources, binding.countries,binding.languages,binding.languages,binding.search,binding.tesla)
        buttons.forEach { button ->
            addScaleEffectToButton(button)

        }

    }
    // Function to apply scale effect on button press


    private fun onButtonClick(view: View) {
        if(isInternetPresent){
        when (view.id) {
            R.id.topHeadLines -> {
                startActivity(HeadLineActivity.getStartIntent(this,""))
            }
            R.id.newsSources -> {
                startActivity(NewsSourceActivity.getStartIntent(this))
            }
            R.id.tesla->{
                startActivity(HeadLineActivity.getStartIntent(this,"1"))
            }
            R.id.countries -> {
                startActivity(CountryActivity.getStartIntent(this,1))
            }
            R.id.languages->{
                startActivity(CountryActivity.getStartIntent(this,2))
            }
            R.id.search->{
               startActivity(SearchActivity.getStartIntent(this))
            }
        }
    }else{
        toastInternetNotAvailable(3)
        }

}}