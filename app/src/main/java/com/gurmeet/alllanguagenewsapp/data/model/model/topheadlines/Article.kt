package com.gurmeet.alllanguagenewsapp.data.model.model.topheadlines

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Article(
    @Keep
    @SerializedName("title")
                      val title: String = "",
    @Keep
                      @SerializedName("description")
                      val description: String = "",
    @Keep
                      @SerializedName("url")
    val url: String = "",
    @Keep
                      @SerializedName("urlToImage")
                      val imageUrl: String = "",
    @Keep
                      @SerializedName("source")
                      val source: Source
                      )
