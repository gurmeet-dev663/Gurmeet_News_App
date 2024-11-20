package com.gurmeet.alllanguagenewsapp.data.model.headlines

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Source(
    @Keep
    @SerializedName("id")
    val id: String? = null,
    @Keep
    @SerializedName("name")
    val name: String = "",
)
