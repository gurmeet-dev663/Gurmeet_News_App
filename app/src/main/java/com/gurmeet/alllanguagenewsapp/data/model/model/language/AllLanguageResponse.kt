package com.gurmeet.alllanguagenewsapp.data.model.model.language

import com.google.gson.annotations.SerializedName


data class AllLanguageResponse(
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("news")
    var news: ArrayList<News> = arrayListOf(),
    @SerializedName("page")
    var page: Int? = null

)