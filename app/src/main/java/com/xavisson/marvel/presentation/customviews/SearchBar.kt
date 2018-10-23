package com.xavisson.marvel.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.xavisson.marvel.R

class SearchBar : FrameLayout {

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(
            context: Context?,
            attrs: AttributeSet?,
            defStyleAttr: Int) : super(context, attrs,
            defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.toolbar_search, this, true)
    }
}