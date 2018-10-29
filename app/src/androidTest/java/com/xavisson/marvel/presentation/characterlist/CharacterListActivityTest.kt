package com.xavisson.marvel.presentation.characterlist

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterListActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(CharacterListActivity::class.java, false, false)

    @Test
    fun spinnerIsShownWhenQueryStarts() {
        val pageObject = launchActivity()
        pageObject.assertLoadingSpinnerIsVisible()
    }

    @Test
    fun spinnerHidesWhenQueryCompleted() {
        val pageObject = launchActivity()
        pageObject.waitUntilInitalSearchFinished()
        pageObject.assertLoadingSpinnerIsGone()
    }

    private fun launchActivity(): CharacterListActivityObject {
        activityRule.launchActivity(null)
        return CharacterListActivityObject(activityRule.activity)
    }
}