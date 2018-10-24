package com.xavisson.marvel.presentation.characterdetail.injector

import android.support.v7.app.AppCompatActivity
import com.xavisson.marvel.injection.PerActivity
import com.xavisson.marvel.injection.components.ActivityComponent
import com.xavisson.marvel.injection.components.ApplicationComponent
import com.xavisson.marvel.injection.modules.ActivityModule
import com.xavisson.marvel.presentation.characterdetail.CharacterDetailActivity
import com.xavisson.marvel.presentation.characterdetail.CharacterDetailPresenter
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class CharacterDetailModule(private val activity: AppCompatActivity) : ActivityModule {

    @Provides
    fun provideCharacterDetailPresenter(): CharacterDetailPresenter {
        return CharacterDetailPresenter()
    }
}

@PerActivity
@Component(
        dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(CharacterDetailModule::class)
)
interface CharacterDetailComponent : ActivityComponent {
    fun inject(activity: CharacterDetailActivity)
}