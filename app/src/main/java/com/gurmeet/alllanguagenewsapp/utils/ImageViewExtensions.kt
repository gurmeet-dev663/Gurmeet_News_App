package com.gurmeet.alllanguagenewsapp.utils

// ImageViewExtensions.kt
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String, placeholder: Int? = null) {
    Glide.with(this.context)
        .load(url)
        .apply {
            placeholder?.let { this.placeholder(it) }
        }
        .into(this)
}

fun ImageView.makeCircular() {
    this.clipToOutline = true
}
