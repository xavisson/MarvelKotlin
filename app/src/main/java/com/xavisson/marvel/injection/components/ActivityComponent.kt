package com.xavisson.marvel.injection.components

import com.xavisson.marvel.injection.PerActivity
import com.xavisson.marvel.injection.modules.ApplicationModule
import dagger.Component

@PerActivity
@Component(
        dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(
                ApplicationModule::class
        ))
interface ActivityComponent {
}