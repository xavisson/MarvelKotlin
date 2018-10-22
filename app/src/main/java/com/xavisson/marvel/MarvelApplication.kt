package com.xavisson.marvel

import android.app.Application
import com.xavisson.marvel.domain.logger.Kog
import com.xavisson.marvel.domain.logger.Logger
import com.xavisson.marvel.logger.AndroidLogger

class MarvelApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MarvelApplication.application = this
        initLogger()
    }
    private fun initLogger() {
        Kog.plant(AndroidLogger())
        Logger.i({ "Logger planted" })
    }

    companion object {
        private lateinit var application: MarvelApplication
        val applicationComponent: ApplicationComponent by lazy {
            val appComponent = DaggerApplicationComponent.builder()
                    .applicationModule(ApplicationModule(application))
                    .build()
            appComponent.inject(application)
            appComponent
        }
    }
}