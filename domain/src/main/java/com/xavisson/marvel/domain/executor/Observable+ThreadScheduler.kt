package com.xavisson.marvel.domain.executor

import io.reactivex.Completable
import io.reactivex.Observable

fun <T> Observable<T>.applyScheduler(scheduler: ThreadScheduler): Observable<T> {
    return compose(scheduler.apply<T>())
}

fun Completable.applyScheduler(scheduler: ThreadScheduler): Completable {
    return compose(scheduler.applyCompletable())
}