package com.gurmeet.alllanguagenewsapp.ui.topheadlines

import android.os.Bundle
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gurmeet.alllanguagenewsapp.AllLanguageApplication

import com.gurmeet.alllanguagenewsapp.data.model.Article
import com.gurmeet.alllanguagenewsapp.databinding.ActivityTopHeadlineBinding
import com.gurmeet.alllanguagenewsapp.di.Component.DaggerActivityComponent
import com.gurmeet.alllanguagenewsapp.di.module.ActivityModule

import com.gurmeet.alllanguagenewsapp.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject


class TopHeadLineActivity : AppCompatActivity() {

    @Inject
     lateinit var newsListViewModel: TopHeadLineViewModel

     @Inject
     lateinit var adapter: TopHeadLineAdapter
    private lateinit var binding: ActivityTopHeadlineBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityTopHeadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

    private fun setupObserver(){
lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED){
        newsListViewModel.uiState.collect{
            when (it){
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
                    Toast.makeText(this@TopHeadLineActivity, it.message, Toast.LENGTH_LONG)
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
            .applicationComponent((application as AllLanguageApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }

}

