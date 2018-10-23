package com.xavisson.marvel.injection.components

import com.xavisson.marvel.MarvelApplication
import com.xavisson.marvel.domain.executor.PostExecutionThread
import com.xavisson.marvel.domain.executor.ThreadExecutor
import com.xavisson.marvel.domain.executor.ThreadScheduler
import com.xavisson.marvel.injection.modules.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class))
interface ApplicationComponent {

    fun inject(application: MarvelApplication)

    fun threadScheduler(): ThreadScheduler

    fun threadExecutor(): ThreadExecutor

    fun postExecutionThread(): PostExecutionThread
}