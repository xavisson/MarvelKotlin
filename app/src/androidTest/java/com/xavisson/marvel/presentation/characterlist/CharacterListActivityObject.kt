package com.xavisson.marvel.presentation.characterlist

import com.schibsted.spain.barista.BaristaAssertions
import com.xavisson.marvel.R

class CharacterListActivityObject(
        val activity: CharacterListActivity
) {
    fun assertLoadingSpinnerIsVisible() {
        BaristaAssertions.assertDisplayed(R.id.loadingSpinner)
    }

    fun assertLoadingSpinnerIsGone() {
        BaristaAssertions.assertNotDisplayed(R.id.loadingSpinner)
    }

    fun waitUntilInitalSearchFinished() {
        Thread.sleep(2000)
    }
}