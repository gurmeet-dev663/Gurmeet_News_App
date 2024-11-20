package com.gurmeet.alllanguagenewsapp.ui.mainactivity.countries

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gurmeet.alllanguagenewsapp.data.model.country.Country
import com.gurmeet.alllanguagenewsapp.databinding.CountryItemLayoutBinding
import com.gurmeet.alllanguagenewsapp.utils.ItemClickListener

class CountryAdapter(
    private val countryList: ArrayList<Country>
) : RecyclerView.Adapter<CountryAdapter.DataViewHolder>() {
    lateinit var itemClickListener: ItemClickListener<Country>

    class DataViewHolder(private val binding: CountryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country, itemClickListener: ItemClickListener<Country>) {
            binding.textViewName.text = country.name
            binding.textViewName.setOnClickListener {
                itemClickListener(country)
            }
            binding.textViewName.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(150).start()
                    }

                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        v.animate().scaleX(1f).scaleY(1f).setDuration(150).start()
                    }
                }
                false // Return false to allow the click listener to also be triggered
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
        holder.bind(countryList[position], itemClickListener)

    fun addData(list: List<Country>) {
        countryList.addAll(list)
    }
}