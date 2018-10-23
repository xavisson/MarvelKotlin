package com.xavisson.marvel.data.adapter

import com.xavisson.marvel.data.exception.MarvelServerExceptionFactory
import io.reactivex.Completable
import io.reactivex.functions.Function
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class RxCompletableErrorCallAdapter constructor(private val wrapped: CallAdapter<Any, Completable>) : CallAdapter<Any, Completable> {
    override fun responseType(): Type {
        return wrapped.responseType()
    }

    @SuppressWarnings("unchecked")
    override fun adapt(call: Call<Any>): Completable {
        return wrapped.adapt(call)
                .onErrorResumeNext(Function { throwable ->
                    Completable.error(MarvelServerExceptionFactory.create(throwable))
                })
    }
}