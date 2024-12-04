package com.gurmeet.alllanguagenewsapp.ui.mainactivity.newsources

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gurmeet.alllanguagenewsapp.databinding.TopSourceItemLayoutBinding
import com.gurmeet.alllanguagenewsapp.utils.ItemClickListener
import com.gurmeet.alllanguagesapp.NewsSources


class NewsSourceAdapter(private val sourceList: ArrayList<NewsSources>)
    : RecyclerView.Adapter<NewsSourceAdapter.DataViewHolder>() {

    lateinit var itemClickListener: ItemClickListener<NewsSources>


    class DataViewHolder(private val binding: TopSourceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sources: NewsSources,itemClickListener: ItemClickListener<NewsSources>) {
            binding.topSources.text = sources.name
            binding.topSources.setOnClickListener{
                itemClickListener(sources)
            }
            binding.topSources.setOnTouchListener { v, event ->
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
    } }


