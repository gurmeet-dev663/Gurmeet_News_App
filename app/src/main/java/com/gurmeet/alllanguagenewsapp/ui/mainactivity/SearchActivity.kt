package com.gurmeet.alllanguagenewsapp.ui.mainactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gurmeet.alllanguagenewsapp.Application
import com.gurmeet.alllanguagenewsapp.data.model.headlines.Article
import com.gurmeet.alllanguagenewsapp.databinding.ActivitySearchBinding
import com.gurmeet.alllanguagenewsapp.di.Component.DaggerActivityComponent
import com.gurmeet.alllanguagenewsapp.di.module.ActivityModule
import com.gurmeet.alllanguagenewsapp.ui.base.BaseActivity
import com.gurmeet.alllanguagenewsapp.ui.base.UiState
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.headlines.HeadLineAdapter
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.headlines.HeadLineViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchActivity : BaseActivity<ActivitySearchBinding,HeadLineViewModel>() {
    @Inject
    lateinit var searchViewModel: HeadLineViewModel

    @Inject
    lateinit var adapter: HeadLineAdapter
var isInternetPresent=false



    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        resetToastCounter()
        networkViewModel.isConnected.observe(this, Observer { isConnected ->
            if (isConnected) {
                isInternetPresent=true
                searchViewModel.createNewsFlow()
                setupUI()
                setupObserver()
            } else {
                isInternetPresent=false
                toastInternetNotAvailable(3)
                binding.progressBar.visibility=View.GONE
            }
        })
        binding.textView.visibility=View.VISIBLE

    }

    override fun inflateBinding(inflater: LayoutInflater): ActivitySearchBinding {
      return ActivitySearchBinding.inflate(inflater)
    }

    override fun getViewModelClass(): Class<HeadLineViewModel> {
     return HeadLineViewModel::class.java
    }

    private fun setupUI() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
     //   binding.searchView.queryHint="Enter Minimum 2 characters"

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
               if(newText.isNotEmpty()){
                   binding.textView.visibility=View.GONE
               }else{
                   binding.textView.visibility=View.VISIBLE
               }
                if(isInternetPresent){
                    searchViewModel.searchNews(newText)
                }else{
                    toastInternetNotAvailable(3)
                }


                return true
            }
        })
    }
private fun setupObserver() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.uiState.collect {
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
                            binding.recyclerView.visibility = View.GONE
                            binding.textView.visibility=View.VISIBLE

                            Toast.makeText(this@SearchActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(articleList: List<Article>) {
     //   adapter.updateData(articleList)
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