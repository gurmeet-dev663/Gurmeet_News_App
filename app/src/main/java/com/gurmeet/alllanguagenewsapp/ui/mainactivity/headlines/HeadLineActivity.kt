package com.gurmeet.alllanguagenewsapp.ui.mainactivity.headlines

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gurmeet.alllanguagenewsapp.Application


import com.gurmeet.alllanguagenewsapp.data.model.headlines.Article

import com.gurmeet.alllanguagenewsapp.databinding.ActivityTopHeadlineBinding
import com.gurmeet.alllanguagenewsapp.di.Component.DaggerActivityComponent
import com.gurmeet.alllanguagenewsapp.di.module.ActivityModule


import com.gurmeet.alllanguagenewsapp.ui.base.BaseActivity

import com.gurmeet.alllanguagenewsapp.ui.base.UiState

import kotlinx.coroutines.launch
import javax.inject.Inject


class HeadLineActivity : BaseActivity<ActivityTopHeadlineBinding,HeadLineViewModel>() {
    companion object {
        const val id = "id"
        fun getStartIntent(context: Context, idGet: String): Intent {
            return Intent(context, HeadLineActivity::class.java)
                .apply {
                    putExtra(id, idGet)
                } }
    }

    @Inject
    lateinit var newsListViewModel: HeadLineViewModel

    @Inject
    lateinit var adapter: HeadLineAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
injectDependencies()
        super.onCreate(savedInstanceState)
        getIntentAndFetchData()
    }

    override fun inflateBinding(inflater: LayoutInflater): ActivityTopHeadlineBinding {
        return ActivityTopHeadlineBinding.inflate(inflater)
    }

    override fun getViewModelClass(): Class<HeadLineViewModel> {
      return HeadLineViewModel::class.java
    }


    private fun getIntentAndFetchData() {
        val id = intent.getStringExtra(id)
        if (id != null && id.isNotEmpty()) {
            if (id.equals("1")) {
                newsListViewModel.fetchTeslaArticles()
            } else {
                newsListViewModel.fetchNewsDetail(id)
            }
        }
        else {
            newsListViewModel.fetchNews()
        }

        setupUI()
        setupObserver()
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
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsListViewModel.uiState.collect {
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
                            Toast.makeText(this@HeadLineActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }

            }
        }
    }

    private fun renderList(articleList: List<Article>) {
        adapter.addData(articleList)
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

