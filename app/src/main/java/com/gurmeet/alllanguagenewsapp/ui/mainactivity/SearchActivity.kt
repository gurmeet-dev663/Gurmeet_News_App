package com.gurmeet.alllanguagenewsapp.ui.mainactivity

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gurmeet.alllanguagenewsapp.AllLanguageApplication
import com.gurmeet.alllanguagenewsapp.R
import com.gurmeet.alllanguagenewsapp.data.model.model.topheadlines.Article
import com.gurmeet.alllanguagenewsapp.databinding.ActivitySearchBinding
import com.gurmeet.alllanguagenewsapp.di.Component.DaggerActivityComponent
import com.gurmeet.alllanguagenewsapp.di.module.ActivityModule
import com.gurmeet.alllanguagenewsapp.ui.base.BaseActivity
import com.gurmeet.alllanguagenewsapp.ui.base.UiState
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.topheadlines.TopHeadLineActivity
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.topheadlines.TopHeadLineAdapter
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.topheadlines.TopHeadLineViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchActivity : BaseActivity<ActivitySearchBinding,TopHeadLineViewModel>() {
    @Inject
    lateinit var searchViewModel: TopHeadLineViewModel

    @Inject
    lateinit var adapter: TopHeadLineAdapter




    companion object {


        fun getStartIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)


        binding.textView.visibility=View.VISIBLE

        setupUI()
        setupObserver()
    }

    override fun inflateBinding(inflater: LayoutInflater): ActivitySearchBinding {
      return ActivitySearchBinding.inflate(inflater)
    }

    override fun getViewModelClass(): Class<TopHeadLineViewModel> {
     return TopHeadLineViewModel::class.java
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
                searchViewModel.searchNews(newText)

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
        adapter.updateData(articleList)
        adapter.notifyDataSetChanged()
    }

    private fun injectDependencies() {

       DaggerActivityComponent.builder()
            .applicationComponent((application as AllLanguageApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}