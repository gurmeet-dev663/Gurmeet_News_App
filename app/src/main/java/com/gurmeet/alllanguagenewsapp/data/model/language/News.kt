package com.gurmeet.alllanguagenewsapp.data.model.language

import com.google.gson.annotations.SerializedName


data class News(

    @SerializedName("id")
    var id: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("author")
    var author: String? = null,
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("language")
    var language: String? = null,
    @SerializedName("category")
    var category: ArrayList<String> = arrayListOf(),
    @SerializedName("published") var published: String? = null

)