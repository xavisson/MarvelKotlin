package com.xavisson.marvel.presentation.navigator

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.xavisson.marvel.presentation.characterdetail.CharacterDetailActivity
import java.lang.ref.WeakReference

interface ActivityNavigator {
    fun goToCharacterDetail()
}

class ApplicationActivityNavigator(
        activity: AppCompatActivity
) : ActivityNavigator {
    private val activityRef = WeakReference(activity)

    private val activity: AppCompatActivity?
        get() = activityRef.get()

    override fun goToCharacterDetail() {
        val intent = Intent(activity, CharacterDetailActivity::class.java)
        activity?.startActivity(intent)
    }
}