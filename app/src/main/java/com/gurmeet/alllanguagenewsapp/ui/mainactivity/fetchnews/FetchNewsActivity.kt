package com.gurmeet.alllanguagenewsapp.ui.mainactivity.fetchnews

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gurmeet.alllanguagenewsapp.Application
import com.gurmeet.alllanguagenewsapp.data.BaseUrl2
import com.gurmeet.alllanguagenewsapp.data.model.language.News
import com.gurmeet.alllanguagenewsapp.databinding.ActivityFetchLanguageBinding


import com.gurmeet.alllanguagenewsapp.di.Component.DaggerActivityComponent
import com.gurmeet.alllanguagenewsapp.di.module.ActivityModule
import com.gurmeet.alllanguagenewsapp.ui.base.BaseActivity
import com.gurmeet.alllanguagenewsapp.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class FetchNewsActivity : BaseActivity<ActivityFetchLanguageBinding,FetchNewsViewModel>() {

    companion object {
        const val id = "id"
       const val  comingFromm="comingFrom"
        fun getStartIntent(context: Context, idGet: String,comingFrom:Int): Intent {
            return Intent(context, FetchNewsActivity::class.java)
                .apply {
                    putExtra(id, idGet)
                    putExtra(comingFromm,comingFrom)

                } }
    }
    @Inject
    lateinit var topLanguageViewModel: FetchNewsViewModel

    @Inject
    lateinit var topLanguageHeadlineAdapter: FetchNewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)

        getIntentAndFetchData()
    }
    private fun getIntentAndFetchData() {
        val id = intent.getStringExtra(FetchNewsActivity.id)
        val comingFrom=intent.getIntExtra(FetchNewsActivity.comingFromm,0)

        if (!id.isNullOrEmpty()) {
            if(comingFrom==1){
                topLanguageViewModel.fetchAllLanguageCountry(id)
            }else{
                topLanguageViewModel.fetchAllLanguage(id)
            }

        }

        setupUI()
        setupObserver()
    }
    override fun inflateBinding(inflater: LayoutInflater): ActivityFetchLanguageBinding {
       return ActivityFetchLanguageBinding.inflate(inflater)
    }

    override fun getViewModelClass(): Class<FetchNewsViewModel> {
        return FetchNewsViewModel::class.java
    }
    private fun setupUI() {
        @BaseUrl2
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = topLanguageHeadlineAdapter
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                topLanguageViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            renderList(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
                        }

                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }

                        is UiState.Error -> {
                            //Handle Error
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@FetchNewsActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }

            }
        }
    }

    private fun renderList(newsList: List<News>) {
        topLanguageHeadlineAdapter.addData(newsList)
        topLanguageHeadlineAdapter.notifyDataSetChanged()
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as Application).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
    override fun onDestroy() {
        super.onDestroy()
        networkViewModel.stopMonitoring()
    }
}