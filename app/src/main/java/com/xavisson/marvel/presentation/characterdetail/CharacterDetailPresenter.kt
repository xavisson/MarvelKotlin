package com.xavisson.marvel.presentation.characterdetail

import com.xavisson.marvel.base.BasePresenter
import com.xavisson.marvel.domain.character.GetCharacterFromIdUseCase
import com.xavisson.marvel.domain.comic.GetComicsFromCharacterIdUseCase
import com.xavisson.marvel.domain.logger.Logger
import com.xavisson.marvel.presentation.characterlist.toUI
import io.reactivex.rxkotlin.subscribeBy

class CharacterDetailPresenter(
        private val getCharacterFromIdUseCase: GetCharacterFromIdUseCase,
        private val getComicsFromCharacterIdUseCase: GetComicsFromCharacterIdUseCase
) : BasePresenter<CharacterDetailView>() {

    var characterId: Int? = null
        set(value) {
            field = value
            field?.let {
                getCharacterDetails()
                getComics()
            }
        }

    fun getCharacterDetails() {
        getCharacterFromIdUseCase.execute(characterId!!).subscribeBy(
                onNext = { getView()?.showCharacterDetails(it.toUI()) }
        )
    }

    fun getComics() {
        getComicsFromCharacterIdUseCase.execute(characterId!!).subscribeBy(
                onNext = { getView()?.showComicList(it.toUI())}
        )
    }

    fun onComicPressed(comic: ComicItemUI) {

    }
}