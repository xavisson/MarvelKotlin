package com.xavisson.marvel.presentation.characterlist

import com.xavisson.marvel.base.BaseView
import com.xavisson.marvel.domain.character.CharacterItem
import com.xavisson.marvel.domain.character.CharacterItemComics

interface CharacterListView : BaseView {
    fun showCharacterList(characterItems: List<CharacterUI>)
    fun showSearchingError()
    fun showLoadingSpinner()
    fun hideLoadingSpinner()
}

interface CharacterUI

data class CharacterItemUI(
        val id: Int,
        val name: String,
        val imageUrl: String?,
        val description: String,
        val comics: CharacterUIComics?
) : CharacterUI

data class CharacterUIComics(
        val available: Int?,
        val collectionURI: String?
)

fun List<CharacterItem>.toUI(): List<CharacterItemUI> {
    return map { it.toUI() }
}

fun CharacterItem.toUI(): CharacterItemUI {
    return CharacterItemUI(
            id = id,
            name = name,
            imageUrl = imageUrl,
            description = description,
            comics = comics?.toUI()
    )
}

fun CharacterItemComics.toUI(): CharacterUIComics {
    return CharacterUIComics(
            available = available,
            collectionURI = collectionURI
    )
}