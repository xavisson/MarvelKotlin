package com.xavisson.marvel.presentation.characterlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pedrogomez.renderers.Renderer
import com.xavisson.marvel.R
import com.xavisson.marvel.domain.logger.Logger
import com.xavisson.marvel.presentation.characterlist.CharacterItemUI
import com.xavisson.marvel.presentation.utils.loadImage
import kotlinx.android.synthetic.main.characterlist_item.view.*

class CharacterItemRenderer(
        private val onClick: (item: CharacterItemUI) -> Unit = {}
) : Renderer<CharacterItemUI>() {

    override fun inflate(inflater: LayoutInflater, parent: ViewGroup): View =
            inflater.inflate(R.layout.characterlist_item, parent, false)

    override fun setUpView(rootView: View) {}

    override fun hookListeners(rootView: View) {
        rootView.setOnClickListener {
            onClick.invoke(content)
        }
    }

    override fun render() {
        with(rootView) {
            name.text = content.name
            image.loadImage(content.imageUrl)
            Logger.d { "xtest_image: ${content.imageUrl}" }
        }
    }
}