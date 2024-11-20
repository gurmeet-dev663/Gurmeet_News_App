package com.gurmeet.alllanguagenewsapp.ui.mainactivity.newsources


import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater

import android.view.View


import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer

import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gurmeet.alllanguagenewsapp.Application
import com.gurmeet.alllanguagenewsapp.databinding.ActivityTopSourceBinding
import com.gurmeet.alllanguagenewsapp.di.Component.DaggerActivityComponent

import com.gurmeet.alllanguagenewsapp.di.module.ActivityModule
import com.gurmeet.alllanguagenewsapp.ui.base.BaseActivity
import com.gurmeet.alllanguagenewsapp.ui.base.UiState
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.SearchActivity
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.headlines.HeadLineActivity

import com.gurmeet.alllanguagesapp.NewsSources
import kotlinx.coroutines.launch
import javax.inject.Inject

 class NewsSourceActivity : BaseActivity<ActivityTopSourceBinding,NewsSourcesViewModel>() {
    @Inject
    lateinit var newsSouceListViewModel: NewsSourcesViewModel

    @Inject
    lateinit var adapter: NewsSourceAdapter
      var isInternetPresent=false

     companion object {
         fun getStartIntent(context: Context): Intent {
             return Intent(context, NewsSourceActivity::class.java)
         }
     }
    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)

        networkViewModel.isConnected.observe(this, Observer { isConnected ->
            if (isConnected) {
                isInternetPresent=true
                newsSouceListViewModel.fetchTopSourceList()
                setupUI()
                setupObserver()
            } else {
             isInternetPresent=false
             toastInternetNotAvailable(3)
                binding.progressBar.visibility=View.GONE
            }
        })
    }

    override fun inflateBinding(inflater: LayoutInflater): ActivityTopSourceBinding {
       return ActivityTopSourceBinding.inflate(inflater)
    }

    override fun getViewModelClass(): Class<NewsSourcesViewModel> {
      return NewsSourcesViewModel::class.java
    }

    private fun setupUI() {
        val recyclerView = binding.recyclerView
        val retryButton=binding.retryButton
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

        adapter.itemClickListener = {
            if(isInternetPresent){
            startActivity(HeadLineActivity.getStartIntent(this, it.id.toString()))
            }else{
           toastInternetNotAvailable(3)
           }
        }
        retryButton.setOnClickListener {
            newsSouceListViewModel.fetchTopSourceList()

        }

    }
    private fun setupObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                newsSouceListViewModel.uiState.collect{
                    when (it){
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.retryButton.visibility=View.GONE
                            renderList(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                            binding.retryButton.visibility=View.GONE
                        }
                        is UiState.Error -> {
                            //Handle Error
                            binding.progressBar.visibility = View.GONE
                            binding.retryButton.visibility=View.VISIBLE

                        } }
                }

            }
        }
    }
    private fun renderList(sourceList: List<NewsSources>) {
        adapter.addData(sourceList)
        adapter.notifyDataSetChanged()
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