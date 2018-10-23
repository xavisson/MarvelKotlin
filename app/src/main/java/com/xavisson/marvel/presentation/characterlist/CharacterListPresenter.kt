package com.xavisson.marvel.presentation.characterlist

import com.xavisson.marvel.base.BasePresenter

class CharacterListPresenter : BasePresenter<CharacterListView>() {

    override fun onCreate() {
        super.onCreate()
        getView()?.showCharacterList(emptyList())
    }

    fun onCharacterPressed(beer: CharacterItemUI) {}

    fun onSearchChanged(search: String) {}
}