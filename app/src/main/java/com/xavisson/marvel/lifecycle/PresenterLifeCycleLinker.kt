package com.xavisson.marvel.lifecycle

import com.xavisson.marvel.base.BasePresenter
import com.xavisson.marvel.base.BaseView
import java.lang.reflect.Modifier

open class PresenterLifeCycleLinker {
    protected val presenters = mutableSetOf<BasePresenter<BaseView>>()
    fun onCreate(view: BaseView) {
        addAnnotatedPresenters(view.javaClass, view)
        setView(view)
        callOnCreate()
    }

    fun onResume(view: BaseView) {
        presenters.forEach { presenter ->
            presenter.setView(view)
            presenter.onResume()
        }
    }

    fun onPause() {
        presenters.forEach { it.onPause() }
    }

    fun onDestroy() {
        presenters.forEach {
            it.onDestroy()
        }
    }

    fun onFirstLayout() {
        presenters.forEach { it.onFirstLayout() }
    }

    private fun addAnnotatedPresenters(kClass: Class<*>, source: Any) {
        if (isOneOfOurNonPresentedClass(kClass)) {
            return
        }
        addAnnotatedPresenters(kClass.superclass, kClass)
        kClass.declaredFields
                .filter { it.isAnnotationPresent(Presenter::class.java) }
                .forEach {
                    if (Modifier.isPrivate(it.modifiers)) {
                        throw PresenterNotAccessibleException("Presenter must be accessible. The visibility can't be private")
                    } else {
                        try {
                            it.isAccessible = true
                            val presenter = it.get(source) as BasePresenter<BaseView>
                            registerPresenter(presenter)
                            it.isAccessible = false
                        } catch (illegalAccessException: IllegalAccessException) {
                            throw PresenterNotAccessibleException(illegalAccessException, "The journeyBasePresenter " + it.name + " of class " + kClass.canonicalName + " can't be accessed")
                        } catch (classCastException: ClassCastException) {
                            throw PresenterException(classCastException,
                                    "The annotation " + Presenter::class.java.canonicalName + " is being used on an object"
                                            + " that is not a " + BasePresenter::class.java.canonicalName + " on the class "
                                            + kClass.canonicalName)
                        }
                    }
                }
    }

    private fun isOneOfOurNonPresentedClass(kClass: Class<*>): Boolean {
        return kClass.kotlin == Any::class
    }

    private fun registerPresenter(presenter: BasePresenter<BaseView>) {
        presenters.add(presenter)
    }

    private fun setView(view: BaseView) {
        presenters.forEach { it.setView(view) }
    }

    private fun callOnCreate() {
        presenters.forEach { it.onCreate() }
    }

    fun onLowMemory() {
        presenters.forEach { it.handleUnexpectedOnDestroy() }
    }
}