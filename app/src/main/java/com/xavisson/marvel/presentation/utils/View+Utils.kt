package com.xavisson.marvel.presentation.utils

import android.view.View
import android.view.animation.DecelerateInterpolator
import kotlinx.android.synthetic.main.characterlist_layout.*

fun View.visible() {
    visibility = View.VISIBLE
}
fun View.gone() {
    visibility = View.GONE
}
fun View.invisible() {
    visibility = View.INVISIBLE
}
fun View.slideUp() {
    animate()
            .translationY(-260f)
            .alpha(1f).setDuration(200)
            .interpolator = DecelerateInterpolator()
}

fun View.slideDown() {
    this.animate()
            .translationY(0f)
            .alpha(1f).setDuration(200)
            .interpolator = DecelerateInterpolator()
}