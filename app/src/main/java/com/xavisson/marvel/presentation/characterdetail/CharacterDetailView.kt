package com.xavisson.marvel.presentation.characterdetail

import com.xavisson.marvel.base.BaseView
import com.xavisson.marvel.domain.comic.ComicItem
import com.xavisson.marvel.domain.logger.Logger
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

fun List<ComicItem>.toUI(): List<ComicItemUI> {
    return map { it.toUI() }
}

fun ComicItem.toUI(): ComicItemUI {
    return ComicItemUI(
            title = title,
            imageUrl = imageUrl
    )
}
