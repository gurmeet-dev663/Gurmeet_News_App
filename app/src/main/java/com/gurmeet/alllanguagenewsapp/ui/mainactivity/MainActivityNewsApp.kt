package com.gurmeet.alllanguagenewsapp.ui.mainactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModel

import com.gurmeet.alllanguagenewsapp.R
import com.gurmeet.alllanguagenewsapp.databinding.ActivityMainNewsAppBinding

import com.gurmeet.alllanguagenewsapp.ui.base.BaseActivity
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.countries.CountryActivity
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.topheadlines.TopHeadLineActivity
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.topsources.TopSourceActivity

class MainActivityNewsApp : BaseActivity<ActivityMainNewsAppBinding,Nothing>(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val topHeadLines = binding.topHeadLines
        val newsSources = binding.newsSources
        val countries = binding.countries
        val languages=binding.languages
        val search=binding.search

        binding.topHeadLines.setOnClickListener { onButtonClick(binding.topHeadLines) }
        binding.newsSources.setOnClickListener { onButtonClick(binding.newsSources) }
        binding.countries.setOnClickListener { onButtonClick(binding.countries) }
        binding.languages.setOnClickListener { onButtonClick(binding.languages) }
        binding.search.setOnClickListener { onButtonClick(binding.search) }
        binding.tesla.setOnClickListener { onButtonClick(binding.tesla) }
    }

    private fun onButtonClick(view: View) {
        when (view.id) {
            R.id.topHeadLines -> {
                val intent=Intent(this,TopHeadLineActivity::class.java)
                startActivity(intent)
            }
            R.id.newsSources -> {
                val intent=Intent(this,TopSourceActivity::class.java)
                startActivity(intent)
            }
            R.id.tesla->{
                startActivity(TopHeadLineActivity.getStartIntent(this,"1"))
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
    }

}