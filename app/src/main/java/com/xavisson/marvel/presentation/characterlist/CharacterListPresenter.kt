package com.xavisson.marvel.presentation.characterlist

import com.xavisson.marvel.base.BasePresenter
import com.xavisson.marvel.domain.character.SearchForCharactersUseCase
import com.xavisson.marvel.domain.reactive.addDisposableTo
import com.xavisson.marvel.presentation.navigator.ActivityNavigator
import io.reactivex.rxkotlin.subscribeBy

class CharacterListPresenter(
        private val searchForCharactersUseCase: SearchForCharactersUseCase,
        private val activityNavigator: ActivityNavigator
) : BasePresenter<CharacterListView>() {

    override fun onCreate() {
        super.onCreate()
        getView()?.showLoadingSpinner()
        onSearchChanged("")
    }

    fun onCharacterPressed(character: CharacterItemUI) {
        activityNavigator.goToCharacterDetail()
    }

    fun onSearchChanged(search: String) {
        searchForCharactersUseCase.execute(search).subscribeBy(
                onNext = {
                    getView()?.hideLoadingSpinner()
                    getView()?.showCharacterList(it.toUI())
                },
                onError = {
                    getView()?.hideLoadingSpinner()
                    getView()?.showSearchingError()
                }
        ).addDisposableTo(disposeBag)
    }
}