package com.xavisson.marvel.presentation.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageLoader(
        private val context: Context
) {
    fun loadImage(
            imageUrl: String?,
            view: ImageView) {
        Glide.with(context).load(imageUrl).into(view)
    }
}