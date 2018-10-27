package com.xavisson.marvel.presentation.characterdetail.injector

import android.support.v7.app.AppCompatActivity
import com.xavisson.marvel.domain.character.CharacterApi
import com.xavisson.marvel.domain.character.CharacterResource
import com.xavisson.marvel.domain.character.GetCharacterFromId
import com.xavisson.marvel.domain.character.GetCharacterFromIdUseCase
import com.xavisson.marvel.domain.comic.ComicResource
import com.xavisson.marvel.domain.comic.GetComicsFromCharacterId
import com.xavisson.marvel.domain.comic.GetComicsFromCharacterIdUseCase
import com.xavisson.marvel.domain.executor.ThreadScheduler
import com.xavisson.marvel.injection.PerActivity
import com.xavisson.marvel.injection.components.ActivityComponent
import com.xavisson.marvel.injection.components.ApplicationComponent
import com.xavisson.marvel.injection.modules.ActivityModule
import com.xavisson.marvel.injection.modules.CharacterModule
import com.xavisson.marvel.presentation.characterdetail.CharacterDetailActivity
import com.xavisson.marvel.presentation.characterdetail.CharacterDetailPresenter
import com.xavisson.marvel.presentation.characterlist.injector.MarvelModule
import com.xavisson.marvel.presentation.navigator.ActivityNavigator
import com.xavisson.marvel.presentation.navigator.ApplicationActivityNavigator
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class CharacterDetailModule(private val activity: AppCompatActivity) : ActivityModule {

    @Provides
    fun provideCharacterDetailPresenter(
            getCharacterFromIdUseCase: GetCharacterFromIdUseCase,
            getComicsFromCharacterIdUseCase: GetComicsFromCharacterIdUseCase,
            activityNavigator: ActivityNavigator
    ): CharacterDetailPresenter {
        return CharacterDetailPresenter(
                getCharacterFromIdUseCase,
                getComicsFromCharacterIdUseCase,
                activityNavigator
        )
    }

    @Provides
    fun provideGetCharacterFromIdUseCase(
            characterResource: CharacterResource,
            threadScheduler: ThreadScheduler
    ): GetCharacterFromIdUseCase {
        return GetCharacterFromId(
                characterResource = characterResource,
                threadScheduler = threadScheduler
        )
    }

    @Provides
    fun provideGetComicsFromCharacterIdUseCase(
            comicResource: ComicResource,
            threadScheduler: ThreadScheduler
    ): GetComicsFromCharacterIdUseCase {
        return GetComicsFromCharacterId(
                comicResource = comicResource,
                threadScheduler = threadScheduler
        )
    }

    @Provides
    fun provideActivityNavigator(): ActivityNavigator = ApplicationActivityNavigator(
            activity
    )
}

@PerActivity
@Component(
        dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(CharacterDetailModule::class, MarvelModule::class)
)
interface CharacterDetailComponent : ActivityComponent {
    fun inject(activity: CharacterDetailActivity)
}