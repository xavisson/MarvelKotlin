package com.xavisson.marvel.presentation.characterdetail

import com.xavisson.marvel.base.BaseView
import com.xavisson.marvel.domain.character.CharacterItem

interface CharacterDetailView : BaseView {
    fun showCharacterDetails(characterDetails: CharacterItem)
}