package com.xavisson.marvel.presentation.characterlist

import com.xavisson.marvel.base.BaseView
import com.xavisson.marvel.domain.character.CharacterItem

interface CharacterListView : BaseView {
    fun showCharacterList(characterItems: List<CharacterUI>)
    fun showSearchingError()
}

interface CharacterUI

data class CharacterItemUI(
        val name: String,
        val imageUrl: String?
) : CharacterUI

fun List<CharacterItem>.toUI(): List<CharacterItemUI> {
    return map { it.toUI() }
}

fun CharacterItem.toUI(): CharacterItemUI {
    return CharacterItemUI(
            name = name,
            imageUrl = imageUrl
    )
}