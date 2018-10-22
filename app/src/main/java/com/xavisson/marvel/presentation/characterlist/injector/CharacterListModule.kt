package com.xavisson.marvel.presentation.characterlist.injector

import android.app.Activity
import com.xavisson.marvel.injection.PerActivity
import com.xavisson.marvel.injection.components.ActivityComponent
import com.xavisson.marvel.injection.components.ApplicationComponent
import com.xavisson.marvel.injection.modules.ActivityModule
import com.xavisson.marvel.presentation.characterlist.CharacterListActivity
import com.xavisson.marvel.presentation.characterlist.CharacterListPresenter
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class CharacterListModule(acitvity: Activity) : ActivityModule {

    @Provides
    fun provideCharacterListPresenter(): CharacterListPresenter {
        return CharacterListPresenter()
    }
}
@PerActivity
@Component(
        dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(CharacterListModule::class)
)
interface CharacterListComponent : ActivityComponent {
    fun inject(activity: CharacterListActivity)
}