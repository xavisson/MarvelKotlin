package com.xavisson.marvel.presentation.characterlist

import com.xavisson.marvel.base.BasePresenter
import com.xavisson.marvel.domain.character.SearchForCharactersUseCase
import com.xavisson.marvel.domain.reactive.addDisposableTo
import io.reactivex.rxkotlin.subscribeBy

class CharacterListPresenter(
        private val searchForCharactersUseCase: SearchForCharactersUseCase
) : BasePresenter<CharacterListView>() {

    override fun onCreate() {
        super.onCreate()
        getView()?.showCharacterList(emptyList())
        onSearchChanged("")
    }

    fun onCharacterPressed(beer: CharacterItemUI) {}

    fun onSearchChanged(search: String) {
        searchForCharactersUseCase.execute(search).subscribeBy(
                onNext = {
                    getView()?.showCharacterList(it.toUI())
                },
                onError = {
                    getView()?.showSearchingError()
                }
        ).addDisposableTo(disposeBag)
    }
}