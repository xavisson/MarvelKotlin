package com.xavisson.marvel.presentation.utils

import android.widget.ImageView

fun ImageView.loadImage(imageUrl: String?) {
    ImageLoader(context).loadImage(
            imageUrl = imageUrl,
            view = this
    )
}