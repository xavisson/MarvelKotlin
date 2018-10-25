package com.xavisson.marvel.presentation.characterdetail

import com.xavisson.marvel.base.BaseView
import com.xavisson.marvel.presentation.characterlist.CharacterItemUI

interface CharacterDetailView : BaseView {
    fun showCharacterDetails(characterDetails: CharacterItemUI)
}