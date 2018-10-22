package com.xavisson.marvel.injection.components

import com.xavisson.marvel.MarvelApplication
import com.xavisson.marvel.injection.modules.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class))
interface ApplicationComponent {
    fun inject(application: MarvelApplication)
}