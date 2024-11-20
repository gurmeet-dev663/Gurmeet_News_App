package com.gurmeet.alllanguagenewsapp.data.model.language

import com.google.gson.annotations.SerializedName


data class LanguageResponse(
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("news")
    var news: ArrayList<News> = arrayListOf(),
    @SerializedName("page")
    var page: Int? = null

)