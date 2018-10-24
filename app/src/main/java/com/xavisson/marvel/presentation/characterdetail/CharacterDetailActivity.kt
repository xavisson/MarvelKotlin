package com.xavisson.marvel.presentation.characterdetail

import android.os.Bundle
import com.xavisson.marvel.MarvelApplication
import com.xavisson.marvel.R
import com.xavisson.marvel.base.BaseActivity
import com.xavisson.marvel.lifecycle.Presenter
import com.xavisson.marvel.presentation.characterdetail.injector.CharacterDetailModule
import com.xavisson.marvel.presentation.characterdetail.injector.DaggerCharacterDetailComponent
import com.xavisson.marvel.presentation.characterlist.CharacterListPresenter.Companion.ID_NOT_FOUND
import com.xavisson.marvel.presentation.navigator.IntentExtras
import javax.inject.Inject

class CharacterDetailActivity : BaseActivity(), CharacterDetailView {

    override val layoutRes: Int = R.layout.characterdetail_layout

    @Presenter
    @Inject
    lateinit var presenter: CharacterDetailPresenter

    override fun initInjector() {
        DaggerCharacterDetailComponent.builder()
                .applicationComponent(MarvelApplication.applicationComponent)
                .characterDetailModule(CharacterDetailModule(this))
                .build()
                .inject(this)
    }

    override fun setupViews() {
        presenter.characterId = intent.getIntExtra(IntentExtras.CHARACTER_ID, ID_NOT_FOUND)
    }
}