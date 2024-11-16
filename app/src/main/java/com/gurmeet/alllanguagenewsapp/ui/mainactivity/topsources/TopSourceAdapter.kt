package com.gurmeet.alllanguagenewsapp.ui.mainactivity.topsources

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import com.gurmeet.alllanguagenewsapp.databinding.TopSourceItemLayoutBinding
import com.gurmeet.alllanguagenewsapp.utils.ItemClickListener
import com.gurmeet.alllanguagesapp.NewsSources


class TopSourceAdapter(private val sourceList: ArrayList<NewsSources>)
    : RecyclerView.Adapter<TopSourceAdapter.DataViewHolder>() {

    lateinit var itemClickListener: ItemClickListener<NewsSources>

    class DataViewHolder(private val binding: TopSourceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sources: NewsSources,itemClickListener: ItemClickListener<NewsSources>) {
            binding.topSources.text = sources.name

            binding.topSources.setOnClickListener{
                itemClickListener(sources)
            }

        }
    }

    /*   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
           DataViewHolder(TopSourceItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
   */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            TopSourceItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = sourceList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(sourceList[position],itemClickListener)

    fun addData(list: List<NewsSources>) {
        sourceList.addAll(list)
    }
}


