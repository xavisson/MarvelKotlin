package com.xavisson.marvel

import android.app.Application
import com.xavisson.marvel.domain.logger.Kog
import com.xavisson.marvel.domain.logger.Logger
import com.xavisson.marvel.logger.AndroidLogger

class MarvelKotlin : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogger()
    }
    private fun initLogger() {
        Kog.plant(AndroidLogger())
        Logger.i({ "Logger planted" })
    }
}