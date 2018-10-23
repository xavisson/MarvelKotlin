package com.xavisson.marvel.data.adapter

import com.xavisson.marvel.data.exception.MarvelServerExceptionFactory
import io.reactivex.Observable
import io.reactivex.functions.Function
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class RxObservableErrorCallAdapter constructor(private val wrapped: CallAdapter<Any, Observable<*>>) : CallAdapter<Any, Observable<*>> {
    override fun responseType(): Type {
        return wrapped.responseType()
    }

    @SuppressWarnings("unchecked")
    override fun adapt(call: Call<Any>): Observable<*> {
        return wrapped.adapt(call)
                .onErrorResumeNext(Function { throwable ->
                    Observable.error(MarvelServerExceptionFactory.create(throwable))
                })
    }
}