package com.xavisson.marvel.presentation.characterlist

import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.pedrogomez.renderers.ListAdapteeCollection
import com.pedrogomez.renderers.RVRendererAdapter
import com.pedrogomez.renderers.RendererBuilder
import com.xavisson.marvel.MarvelApplication
import com.xavisson.marvel.R
import com.xavisson.marvel.base.BaseActivity
import com.xavisson.marvel.lifecycle.Presenter
import com.xavisson.marvel.presentation.characterlist.adapter.CharacterItemRenderer
import com.xavisson.marvel.presentation.characterlist.injector.CharacterListModule
import com.xavisson.marvel.presentation.characterlist.injector.DaggerCharacterListComponent
import kotlinx.android.synthetic.main.characterlist_layout.*
import javax.inject.Inject

class CharacterListActivity : BaseActivity(), CharacterListView {

    override val layoutRes: Int = R.layout.characterlist_layout

    lateinit var charactersAdapter: RVRendererAdapter<CharacterUI>

    @Presenter
    @Inject
    lateinit var presenter: CharacterListPresenter

    override fun setupViews() {
        setupAdapter()
        setupRecyclerView()
    }

    override fun initInjector() {
        DaggerCharacterListComponent.builder()
                .applicationComponent(MarvelApplication.applicationComponent)
                .characterListModule(CharacterListModule(this))
                .build()
                .inject(this)
    }

    override fun showCharacterList(characterItems: List<CharacterUI>) {
        charactersAdapter.diffUpdate(characterItems)
    }
    private fun setupAdapter() {
        val rendererBuilder = RendererBuilder<CharacterUI>()
                .bind(CharacterItemUI::class.java, CharacterItemRenderer({ presenter.onCharacterPressed(it) }))
        charactersAdapter = RVRendererAdapter(rendererBuilder, ListAdapteeCollection())
    }
    private fun setupRecyclerView() {
        characterList.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        characterList.setHasFixedSize(true)
        characterList.adapter = charactersAdapter
    }
}