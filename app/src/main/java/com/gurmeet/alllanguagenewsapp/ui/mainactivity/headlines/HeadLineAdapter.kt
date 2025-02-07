package com.gurmeet.alllanguagenewsapp.ui.mainactivity.headlines

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gurmeet.alllanguagenewsapp.data.model.headlines.Article
import com.gurmeet.alllanguagenewsapp.databinding.TopHeadlineItemLayoutBinding
import com.gurmeet.alllanguagenewsapp.utils.AppUtils
import com.gurmeet.alllanguagenewsapp.utils.loadImage

class HeadLineAdapter : PagingDataAdapter<Any, HeadLineAdapter.DataViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                return (oldItem as? Article)?.url == (newItem as? Article)?.url
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem
            }
        }
    }

    class DataViewHolder(private val binding: TopHeadlineItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.textViewTitle.text = article.title
            binding.textViewDescription.text = article.description
            binding.textViewSource.text = article.source.name

            // Use Coil for image loading (since Glide is commented out in your code)
            if (!article.imageUrl.isNullOrEmpty()) {

                binding.imageViewBanner.loadImage(article.imageUrl)
            }

            itemView.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(it.context, Uri.parse(article.url))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = TopHeadlineItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        getItem(position)?.let { article -> holder.bind(article as Article) }
    }
 /*   fun addData(list: List<Article>) {
        articleList.addAll(list)
    }

    fun updateData(list: List<Article>) {
        articleList.clear()
        articleList.addAll(list)
    }*/

    // Comparator to check if items are the same (helps with efficient diffing)
    object ArticleComparator : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title // You can change this to another unique identifier if needed
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem // Comparing entire object
        }
    }
}