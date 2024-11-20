package com.gurmeet.alllanguagenewsapp.ui.mainactivity.fetchnews

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gurmeet.alllanguagenewsapp.data.model.language.News
import com.gurmeet.alllanguagenewsapp.databinding.TopHeadlineItemLayoutBinding

class FetchNewsAdapter (private val newsList: ArrayList<News>)
    : RecyclerView.Adapter<FetchNewsAdapter.DataViewHolder>() {


    class DataViewHolder(private val binding: TopHeadlineItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {

            binding.textViewTitle.text = news.title
            binding.textViewDescription.text = news.description
            binding.textViewSource.text = news.author
            Glide.with(binding.imageViewBanner.context)
                .load(news.image)
                .into(binding.imageViewBanner)
            itemView.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(it.context, Uri.parse(news.url))
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            TopHeadlineItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(newsList[position])

    fun addData(list: List<News>) {
        newsList.addAll(list)
    }

    fun updateData(list: List<News>) {
        newsList.clear()
        newsList.addAll(list)
    }
}