package com.gurmeet.alllanguagenewsapp.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

class AppUtils {
    fun ImageView.loadImage(url:String){
        Glide.with(this).load(url).into(this)
    }
}