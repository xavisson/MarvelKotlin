package com.xavisson.marvel.domain.reactive

import io.reactivex.disposables.Disposable
import java.util.*

class DisposeBag {

    private val disposableCollection: MutableCollection<Disposable> = LinkedList()

    fun dispose() {
        disposableCollection.forEach(Disposable::dispose)
        disposableCollection.clear()
    }

    fun add(disposable: Disposable) {
        disposableCollection.add(disposable)
    }

    fun remove(disposable: Disposable) {
        disposableCollection.remove(disposable)
        disposable.dispose()
    }
}

fun Disposable.addDisposableTo(bag: DisposeBag): Disposable = apply { bag.add(this) }