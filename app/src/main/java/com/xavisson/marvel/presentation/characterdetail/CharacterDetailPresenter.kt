package com.xavisson.marvel.presentation.characterdetail

import com.xavisson.marvel.base.BasePresenter
import com.xavisson.marvel.domain.character.GetCharacterFromIdUseCase
import com.xavisson.marvel.domain.logger.Logger
import com.xavisson.marvel.presentation.characterlist.toUI
import io.reactivex.rxkotlin.subscribeBy

class CharacterDetailPresenter(
        private val getCharacterFromIdUseCase: GetCharacterFromIdUseCase
) : BasePresenter<CharacterDetailView>() {

    var characterId: Int? = null
        set(value) {
            field = value
            field?.let { getCharacterDetails() }
        }

    fun getCharacterDetails() {
        getCharacterFromIdUseCase.execute(characterId!!).subscribeBy(
                onNext = { getView()?.showCharacterDetails(it.toUI())}
        )
    }

    fun onComicPressed(comic: ComicItemUI) {

    }
}