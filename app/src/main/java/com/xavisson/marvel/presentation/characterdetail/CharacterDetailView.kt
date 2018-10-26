package com.xavisson.marvel.presentation.characterdetail

import com.xavisson.marvel.base.BaseView
import com.xavisson.marvel.presentation.characterlist.CharacterItemUI

interface CharacterDetailView : BaseView {
    fun showCharacterDetails(characterDetails: CharacterItemUI)
    fun showComicList(comicItems: List<ComicUI>)
}

interface ComicUI

data class ComicItemUI(
        val title: String,
        val imageUrl: String?
) : ComicUI

