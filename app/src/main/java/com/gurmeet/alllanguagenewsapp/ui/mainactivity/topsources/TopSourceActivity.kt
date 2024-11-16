package com.gurmeet.alllanguagenewsapp.ui.mainactivity.topsources

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gurmeet.alllanguagenewsapp.AllLanguageApplication
import com.gurmeet.alllanguagenewsapp.databinding.ActivityTopSourceBinding
import com.gurmeet.alllanguagenewsapp.di.Component.DaggerActivityComponent

import com.gurmeet.alllanguagenewsapp.di.module.ActivityModule
import com.gurmeet.alllanguagenewsapp.ui.base.BaseActivity
import com.gurmeet.alllanguagenewsapp.ui.base.UiState
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.topheadlines.TopHeadLineActivity
import com.gurmeet.alllanguagesapp.NewsSources
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopSourceActivity : BaseActivity<ActivityTopSourceBinding,TopSourcesViewModel>() {
    @Inject
    lateinit var newsSouceListViewModel: TopSourcesViewModel

    @Inject
    lateinit var adapter: TopSourceAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)

        setupUI()
        setupObserver()


    }

    override fun inflateBinding(inflater: LayoutInflater): ActivityTopSourceBinding {
       return ActivityTopSourceBinding.inflate(inflater)
    }

    override fun getViewModelClass(): Class<TopSourcesViewModel> {
      return TopSourcesViewModel::class.java
    }

    private fun setupUI() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter
        adapter.itemClickListener = {
            startActivity(TopHeadLineActivity.getStartIntent(this, it.id.toString()))

        }
    }
    private fun setupObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                newsSouceListViewModel.uiState.collect{
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
                            Toast.makeText(this@TopSourceActivity, it.message, Toast.LENGTH_LONG)
                                .show()
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
            .applicationComponent((application as AllLanguageApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }


}