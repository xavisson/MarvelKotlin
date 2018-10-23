package com.xavisson.marvel.presentation.characterlist

import com.xavisson.marvel.base.BaseView

interface CharacterListView : BaseView {
    fun showCharacterList(characterItems: List<CharacterUI>)
}

interface CharacterUI

data class CharacterItemUI(
        val name: String,
        val imageUrl: String
) : CharacterUI
