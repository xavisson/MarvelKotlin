package com.xavisson.marvel.presentation.characterlist

import com.xavisson.marvel.MarvelApplication
import com.xavisson.marvel.R
import com.xavisson.marvel.base.BaseActivity
import com.xavisson.marvel.lifecycle.Presenter
import com.xavisson.marvel.presentation.characterlist.injector.CharacterListModule
import com.xavisson.marvel.presentation.characterlist.injector.DaggerCharacterListComponent
import javax.inject.Inject

class CharacterListActivity : BaseActivity(), CharacterListView {

    override val layoutRes: Int = R.layout.characterlist_layout

    @Presenter
    @Inject
    lateinit var presenter: CharacterListPresenter

    override fun initInjector() {
        DaggerCharacterListComponent.builder()
                .applicationComponent(MarvelApplication.applicationComponent)
                .characterListModule(CharacterListModule(this))
                .build()
                .inject(this)
    }
}