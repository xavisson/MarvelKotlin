package com.xavisson.marvel.presentation.navigator

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.xavisson.marvel.presentation.characterdetail.CharacterDetailActivity
import com.xavisson.marvel.presentation.navigator.IntentExtras.CHARACTER_ID
import java.lang.ref.WeakReference

interface ActivityNavigator {
    fun goToCharacterDetail(characterId: Int)
    fun closeDetail()
}

class ApplicationActivityNavigator(
        activity: AppCompatActivity
) : ActivityNavigator {
    private val activityRef = WeakReference(activity)

    private val activity: AppCompatActivity?
        get() = activityRef.get()

    override fun goToCharacterDetail(characterId: Int) {
        val intent = Intent(activity, CharacterDetailActivity::class.java)
        intent.putExtra(CHARACTER_ID, characterId)
        activity?.startActivity(intent)
    }

    override fun closeDetail() {
        activity?.onBackPressed()
    }
}

object IntentExtras {
    const val CHARACTER_ID = "CHARACTER_ID"
}