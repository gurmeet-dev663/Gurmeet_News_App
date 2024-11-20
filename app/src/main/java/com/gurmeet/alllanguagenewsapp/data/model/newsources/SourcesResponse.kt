package com.gurmeet.alllanguagesapp

import com.google.gson.annotations.SerializedName



data class SourcesResponse (

  @SerializedName("status")
  var status  : String? = null,

  @SerializedName("sources")
var sources : ArrayList<NewsSources> = arrayListOf()


)