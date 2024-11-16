package com.gurmeet.alllanguagesapp

import com.google.gson.annotations.SerializedName



data class TopSourcesResponse (

  @SerializedName("status")
  var status  : String? = null,

  @SerializedName("sources")
var sources : ArrayList<NewsSources> = arrayListOf()


)