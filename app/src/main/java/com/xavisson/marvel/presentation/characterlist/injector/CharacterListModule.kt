package com.xavisson.marvel.presentation.characterlist.injector

import android.app.Activity
import com.xavisson.marvel.domain.character.CharacterResource
import com.xavisson.marvel.domain.character.SearchForCharacters
import com.xavisson.marvel.domain.character.SearchForCharactersUseCase
import com.xavisson.marvel.domain.executor.ThreadScheduler
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
    fun provideCharacterListPresenter(
            searchForCharactersUseCase: SearchForCharactersUseCase
    ): CharacterListPresenter {
        return CharacterListPresenter(
                searchForCharactersUseCase
        )
    }

    @Provides
    fun provideSearchForCharactersUseCase(
            characterResource: CharacterResource,
            threadScheduler: ThreadScheduler
    ): SearchForCharactersUseCase {
        return SearchForCharacters(
                characterResource = characterResource,
                threadScheduler = threadScheduler
        )
    }

    @Provides
    fun provideBeerResource(
    ): CharacterResource {
        return CharacterResource()
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