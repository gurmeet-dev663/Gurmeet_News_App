package com.gurmeet.alllanguagenewsapp.ui.mainactivity.headlines

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gurmeet.alllanguagenewsapp.data.model.headlines.Article
import com.gurmeet.alllanguagenewsapp.databinding.TopHeadlineItemLayoutBinding
import com.gurmeet.alllanguagenewsapp.utils.AppUtils
import com.gurmeet.alllanguagenewsapp.utils.loadImage

class HeadLineAdapter (private val articleList: ArrayList<Article>)
    : RecyclerView.Adapter<HeadLineAdapter.DataViewHolder>() {


    class DataViewHolder(private val binding: TopHeadlineItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {

                binding.textViewTitle.text = article.title
                binding.textViewDescription.text = article.description
                binding.textViewSource.text = article.source.name
               /* Glide.with(binding.imageViewBanner.context)
                    .load(article.imageUrl)
                    .into(binding.imageViewBanner)*/
            if(!article.imageUrl.isNullOrEmpty()){
                binding.imageViewBanner.loadImage(article.imageUrl)
            }

            itemView.setOnClickListener {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(it.context, Uri.parse(article.url))
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

    override fun getItemCount(): Int = articleList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(articleList[position])

    fun addData(list: List<Article>) {
        articleList.addAll(list)
    }

    fun updateData(list: List<Article>) {
        articleList.clear()
        articleList.addAll(list)
    }


}