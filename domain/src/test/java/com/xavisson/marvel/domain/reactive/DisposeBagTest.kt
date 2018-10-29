package com.xavisson.marvel.domain.reactive

import com.nhaarman.mockito_kotlin.mock
import io.reactivex.disposables.Disposable
import org.amshove.kluent.shouldEqual
import org.junit.Test
import org.mockito.Mockito

class DisposeBagTest {

    @Test
    fun beEmptyAnCollection() {
        val disposeBag = givenAnEmptyDisposeBag()
        disposeBag.disposableCollection.size shouldEqual 0
    }

    @Test
    fun addADisposableToTheCollection() {
        val disposeBag = givenAnEmptyDisposeBag()
        val anyDisposable = givenMockedDisposable()
        disposeBag.add(anyDisposable)
        disposeBag.disposableCollection.size shouldEqual 1
    }

    @Test
    fun removeADisposableFromTheCollectionWhenClear() {
        val disposeBag = givenAnEmptyDisposeBag()
        val anyDisposable = givenMockedDisposable()
        disposeBag.add(anyDisposable)
        disposeBag.dispose()
        Mockito.verify(anyDisposable).dispose()
        disposeBag.disposableCollection.size shouldEqual 0
    }

    private fun givenAnEmptyDisposeBag(): DisposeBag {
        return DisposeBag()
    }

    private fun givenMockedDisposable(): Disposable {
        return mock()
    }
}