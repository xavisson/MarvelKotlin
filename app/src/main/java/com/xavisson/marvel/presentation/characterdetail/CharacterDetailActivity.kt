package com.xavisson.marvel.presentation.characterdetail

import com.xavisson.marvel.R
import com.xavisson.marvel.base.BaseActivity

class CharacterDetailActivity : BaseActivity(), CharacterDetailView {

    override val layoutRes: Int = R.layout.characterdetail_layout

    override fun initInjector() {
    }
}