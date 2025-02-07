package com.gurmeet.alllanguagenewsapp.data.model.headlines

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class HeadlineResponse(
    @Keep
    @SerializedName("status")
    val status: String = "",
    @Keep
    @SerializedName("totalResults")
    val totalResults: Int = 0,
    @Keep
    @SerializedName("articles")
    val articles: List<Article> = ArrayList(),

    val total: Int,
    val per_page: Int,
    val page: Int
)
