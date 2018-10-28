package com.xavisson.marvel.presentation.characterlist.injector

import android.support.v7.app.AppCompatActivity
import com.xavisson.marvel.domain.character.CharacterResource
import com.xavisson.marvel.domain.character.SearchForCharacters
import com.xavisson.marvel.domain.character.SearchForCharactersUseCase
import com.xavisson.marvel.domain.executor.ThreadScheduler
import com.xavisson.marvel.injection.PerActivity
import com.xavisson.marvel.injection.components.ActivityComponent
import com.xavisson.marvel.injection.components.ApplicationComponent
import com.xavisson.marvel.injection.modules.ActivityModule
import com.xavisson.marvel.injection.modules.MarvelModule
import com.xavisson.marvel.presentation.characterlist.CharacterListActivity
import com.xavisson.marvel.presentation.characterlist.CharacterListPresenter
import com.xavisson.marvel.presentation.navigator.ActivityNavigator
import com.xavisson.marvel.presentation.navigator.ApplicationActivityNavigator
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class CharacterListModule(private val activity: AppCompatActivity) : ActivityModule {

    @Provides
    fun provideCharacterListPresenter(
            searchForCharactersUseCase: SearchForCharactersUseCase,
            activityNavigator: ActivityNavigator
    ): CharacterListPresenter {
        return CharacterListPresenter(
                searchForCharactersUseCase,
                activityNavigator
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
    fun provideActivityNavigator(): ActivityNavigator = ApplicationActivityNavigator(
            activity
    )
}

@PerActivity
@Component(
        dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(CharacterListModule::class)
)
interface CharacterListComponent : ActivityComponent {
    fun inject(activity: CharacterListActivity)
}