package com.xavisson.marvel.presentation.characterdetail

import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.pedrogomez.renderers.ListAdapteeCollection
import com.pedrogomez.renderers.RVRendererAdapter
import com.pedrogomez.renderers.RendererBuilder
import com.xavisson.marvel.MarvelApplication
import com.xavisson.marvel.R
import com.xavisson.marvel.base.BaseActivity
import com.xavisson.marvel.lifecycle.Presenter
import com.xavisson.marvel.presentation.characterdetail.adapter.ComicItemRenderer
import com.xavisson.marvel.presentation.characterdetail.injector.CharacterDetailModule
import com.xavisson.marvel.presentation.characterdetail.injector.DaggerCharacterDetailComponent
import com.xavisson.marvel.presentation.characterlist.CharacterItemUI
import com.xavisson.marvel.presentation.characterlist.CharacterListPresenter.Companion.ID_NOT_FOUND
import com.xavisson.marvel.presentation.navigator.IntentExtras
import com.xavisson.marvel.presentation.utils.gone
import com.xavisson.marvel.presentation.utils.loadImage
import com.xavisson.marvel.presentation.utils.visible
import kotlinx.android.synthetic.main.characterdetail_layout.*
import javax.inject.Inject

class CharacterDetailActivity : BaseActivity(), CharacterDetailView {

    override val layoutRes: Int = R.layout.characterdetail_layout

    lateinit var comicsAdapter: RVRendererAdapter<ComicUI>

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
        setupAdapter()
        setupRecyclerView()
    }

    override fun showCharacterDetails(characterDetails: CharacterItemUI) {
        characterImage.loadImage(characterDetails.imageUrl)
        toolbar.title = characterDetails.name
        description.text = characterDetails.description
        comicsAvailable.text = characterDetails.comics?.available.toString()
    }

    override fun showComicList(comicItems: List<ComicUI>) {
        if (comicItems.hasItems()) {
            comicList.visible()
            noComicsAvailable.gone()
            comicsAdapter.diffUpdate(comicItems)
        } else {
            comicList.gone()
            noComicsAvailable.visible()
        }
    }

    private fun List<ComicUI>.hasItems(): Boolean {
        return size > 0
    }

    private fun setupAdapter() {
        val rendererBuilder = RendererBuilder<ComicUI>()
                .bind(ComicItemUI::class.java, ComicItemRenderer({ presenter.onComicPressed(it) }))
        comicsAdapter = RVRendererAdapter(rendererBuilder, ListAdapteeCollection())
    }

    private fun setupRecyclerView() {
        comicList.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        comicList.setHasFixedSize(true)
        comicList.adapter = comicsAdapter
    }
}