package com.xavisson.marvel.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xavisson.marvel.lifecycle.PresenterLifeCycleLinker

abstract class BaseActivity : AppCompatActivity(), BaseView {
    abstract val layoutRes: Int
    private val presenterLifeCycleLinker = PresenterLifeCycleLinker()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        setupViews()
        presenterLifeCycleLinker.onCreate(this)
    }

    override fun onResume() {
        super.onResume()
        presenterLifeCycleLinker.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        presenterLifeCycleLinker.onPause()
    }

    override fun onDestroy() {
        presenterLifeCycleLinker.onDestroy()
        super.onDestroy()
    }

    open fun setupViews() {}
    override fun onLowMemory() {
        presenterLifeCycleLinker.onLowMemory()
        super.onLowMemory()
    }
}