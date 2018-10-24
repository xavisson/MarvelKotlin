package com.xavisson.marvel.presentation.characterlist

import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
import kotlinx.android.synthetic.main.toolbar_search.*
import javax.inject.Inject
import android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE
import com.xavisson.marvel.presentation.utils.*


class CharacterListActivity : BaseActivity(), CharacterListView {

    override val layoutRes: Int = R.layout.characterlist_layout

    private lateinit var charactersAdapter: RVRendererAdapter<CharacterUI>

    @Presenter
    @Inject
    lateinit var presenter: CharacterListPresenter

    override fun setupViews() {
        setupAdapter()
        setupRecyclerView()

        searchText.setupEditTextOnTextChangedListener(onTextChangedBlock = {
            presenter.onSearchChanged(it)
        })
    }

    override fun initInjector() {
        DaggerCharacterListComponent.builder()
                .applicationComponent(MarvelApplication.applicationComponent)
                .characterListModule(CharacterListModule(this))
                .build()
                .inject(this)
    }

    override fun showCharacterList(characterItems: List<CharacterUI>) {
        if (characterItems.hasItems()) {
            characterList.visible()
            emptyLayout.gone()
            charactersAdapter.diffUpdate(characterItems)
        } else {
            characterList.gone()
            emptyLayout.visible()
        }
    }

    override fun showSearchingError() {
        Snackbar.make(charactersLayout, R.string.error_searching, Snackbar.LENGTH_LONG)
                .show()
    }

    override fun hideLoadingSpinner() {
        loadingSpinner.gone()
    }

    override fun showLoadingSpinner() {
        loadingSpinner.visible()
    }

    private fun List<CharacterUI>.hasItems(): Boolean {
        return size > 0
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
        characterList.addOnScrollListener(characterListOnScrollListener)
    }

    private val characterListOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == SCROLL_STATE_IDLE) {
                searchBar.slideDown()
            } else {
                searchBar.slideUp()
            }
        }
    }
}