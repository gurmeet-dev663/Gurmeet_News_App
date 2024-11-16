package com.gurmeet.alllanguagenewsapp.ui.mainactivity.countries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gurmeet.alllanguagenewsapp.data.model.model.country.Country
import com.gurmeet.alllanguagenewsapp.data.model.model.language.News
import com.gurmeet.alllanguagenewsapp.databinding.CountryItemLayoutBinding
import com.gurmeet.alllanguagenewsapp.utils.ItemClickListener
import com.gurmeet.alllanguagesapp.NewsSources

class CountryAdapter (
    private val countryList: ArrayList<Country>
) : RecyclerView.Adapter<CountryAdapter.DataViewHolder>() {
    lateinit var itemClickListener: ItemClickListener<Country>
    class DataViewHolder(private val binding: CountryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country,itemClickListener: ItemClickListener<Country>) {
            binding.textViewName.text = country.name
            binding.textViewName.setOnClickListener{
                itemClickListener(country)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            CountryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = countryList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(countryList[position],itemClickListener)

    fun addData(list: List<Country>) {
        countryList.addAll(list)
    } }