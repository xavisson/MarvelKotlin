package com.xavisson.marvel.presentation.characterlist

import com.xavisson.marvel.base.BasePresenter

class CharacterListPresenter : BasePresenter<CharacterListView>() {

    override fun onCreate() {
        super.onCreate()
        getView()?.showCharacterList(listOf())
    }

    fun onCharacterPressed(beer: CharacterItemUI) {}
}