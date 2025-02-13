package com.gurmeet.alllanguagenewsapp.ui.mainactivity.headlines

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gurmeet.alllanguagenewsapp.data.model.headlines.Article
import com.gurmeet.alllanguagenewsapp.databinding.TopHeadlineItemLayoutBinding
import com.gurmeet.alllanguagenewsapp.utils.loadImage

class HeadLineAdaperWithoutPaging3  (private val articleList: ArrayList<Article>)
    : RecyclerView.Adapter<HeadLineAdaperWithoutPaging3.DataViewHolder>() {


    class DataViewHolder(private val binding: TopHeadlineItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {

            binding.textViewTitle.text = article.title
            binding.textViewDescription.text = article.description
            binding.textViewSource.text = article.source.name
            article.imageUrl?.let { binding.imageViewBanner.loadImage(it) }
        /*    Glide.with(binding.imageViewBanner.context)
                .load(article.imageUrl)
                .into(binding.imageViewBanner)*/
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

    override fun onBindViewHolder(holder: HeadLineAdaperWithoutPaging3.DataViewHolder, position: Int) =
        holder.bind(articleList[position])


    override fun getItemCount(): Int = articleList.size

  /*  override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(articleList[position])
*/
    fun addData(list: List<Article>) {
        articleList.addAll(list)
    }

    fun updateData(list: List<Article>) {
        articleList.clear()
        articleList.addAll(list)
    }
}