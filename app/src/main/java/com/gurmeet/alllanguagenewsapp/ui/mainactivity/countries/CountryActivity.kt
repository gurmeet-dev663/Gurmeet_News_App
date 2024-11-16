package com.gurmeet.alllanguagenewsapp.ui.mainactivity.countries

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
import com.gurmeet.alllanguagenewsapp.data.model.model.country.Country
import com.gurmeet.alllanguagenewsapp.databinding.ActivityCountryBinding
import com.gurmeet.alllanguagenewsapp.databinding.CountryItemLayoutBinding
import com.gurmeet.alllanguagenewsapp.di.Component.DaggerActivityComponent
import com.gurmeet.alllanguagenewsapp.di.module.ActivityModule
import com.gurmeet.alllanguagenewsapp.ui.base.BaseActivity
import com.gurmeet.alllanguagenewsapp.ui.base.UiState
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.language.TopLanguageSearchActivity
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.topheadlines.TopHeadLineActivity
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountryActivity : BaseActivity<ActivityCountryBinding,CountryViewModel>() {
var id = 0

    companion object {
        const val id = "id"
        fun getStartIntent(context: Context,comingFrom:Int): Intent {
            return Intent(context, CountryActivity::class.java)
                .apply { putExtra("id",comingFrom) }
        }
    }


    @Inject
    lateinit var countriesViewModel: CountryViewModel

    @Inject
    lateinit var adapter: CountryAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)

       getIntentAndFetchData()
    }

    override fun inflateBinding(inflater: LayoutInflater): ActivityCountryBinding {
     return ActivityCountryBinding.inflate(inflater)
    }

    override fun getViewModelClass(): Class<CountryViewModel> {
return CountryViewModel::class.java
    }

    private fun getIntentAndFetchData() {
         id = intent.getIntExtra(CountryActivity.id,0)

        if (id!=0) {
            if (id==1) {
                countriesViewModel.fetchCountries()
            } else {
                countriesViewModel.fetchLanguages()
            }
        }
        else {
            countriesViewModel.fetchCountries()
        }

        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter
        adapter.itemClickListener = {
            startActivity(TopLanguageSearchActivity.getStartIntent(this, it.id.toString(),id))

        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                countriesViewModel.uiState.collect {
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
                            Toast.makeText(this@CountryActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(countryList: List<Country>) {
        adapter.addData(countryList)
        adapter.notifyDataSetChanged()
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as AllLanguageApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }

}