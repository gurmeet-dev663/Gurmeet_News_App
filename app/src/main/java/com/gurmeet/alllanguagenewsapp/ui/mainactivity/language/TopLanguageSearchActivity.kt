package com.gurmeet.alllanguagenewsapp.ui.mainactivity.language

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gurmeet.alllanguagenewsapp.AllLanguageApplication
import com.gurmeet.alllanguagenewsapp.R
import com.gurmeet.alllanguagenewsapp.data.BaseUrl2
import com.gurmeet.alllanguagenewsapp.data.api.NetworkService
import com.gurmeet.alllanguagenewsapp.data.model.model.language.News
import com.gurmeet.alllanguagenewsapp.data.model.model.topheadlines.Article
import com.gurmeet.alllanguagenewsapp.databinding.ActivityTopLanguageSearchBinding
import com.gurmeet.alllanguagenewsapp.di.Component.DaggerActivityComponent
import com.gurmeet.alllanguagenewsapp.di.module.ActivityModule
import com.gurmeet.alllanguagenewsapp.ui.base.BaseActivity
import com.gurmeet.alllanguagenewsapp.ui.base.UiState
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.topheadlines.TopHeadLineActivity
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.topheadlines.TopHeadLineAdapter
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.topheadlines.TopHeadLineViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

class TopLanguageSearchActivity : BaseActivity<ActivityTopLanguageSearchBinding,TopLanguageViewModel>() {

    companion object {
        const val id = "id"
       const val  comingFromm="comingFrom"
        fun getStartIntent(context: Context, idGet: String,comingFrom:Int): Intent {
            return Intent(context, TopLanguageSearchActivity::class.java)
                .apply {
                    putExtra(id, idGet)
                    putExtra(comingFromm,comingFrom)

                } }
    }
    @Inject
    lateinit var topLanguageViewModel: TopLanguageViewModel

    @Inject
    lateinit var topLanguageHeadlineAdapter: TopLanguageHeadlineAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)

        getIntentAndFetchData()
    }
    private fun getIntentAndFetchData() {
        val id = intent.getStringExtra(TopLanguageSearchActivity.id)
        val comingFrom=intent.getIntExtra(TopLanguageSearchActivity.comingFromm,0)

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
    override fun inflateBinding(inflater: LayoutInflater): ActivityTopLanguageSearchBinding {
       return ActivityTopLanguageSearchBinding.inflate(inflater)
    }

    override fun getViewModelClass(): Class<TopLanguageViewModel> {
        return TopLanguageViewModel::class.java
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
                            Toast.makeText(this@TopLanguageSearchActivity, it.message, Toast.LENGTH_LONG)
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
            .applicationComponent((application as AllLanguageApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)


    }

}