package com.xavisson.marvel.presentation.characterdetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pedrogomez.renderers.Renderer
import com.xavisson.marvel.R
import com.xavisson.marvel.presentation.characterdetail.ComicItemUI

class ComicItemRenderer(
        private val onClick: (item: ComicItemUI) -> Unit = {}
) : Renderer<ComicItemUI>() {

    override fun inflate(inflater: LayoutInflater, parent: ViewGroup): View =
            inflater.inflate(R.layout.comiclist_item, parent, false)

    override fun setUpView(rootView: View) {}

    override fun hookListeners(rootView: View) {
        rootView.setOnClickListener {
            onClick.invoke(content)
        }
    }

    override fun render() {
        with(rootView) {
            //            image.loadImage(content.imageUrl)
        }
    }
}