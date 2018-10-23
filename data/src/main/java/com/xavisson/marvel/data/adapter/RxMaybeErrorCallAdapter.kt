package com.xavisson.marvel.data.adapter

import com.xavisson.marvel.data.exception.MarvelServerExceptionFactory
import io.reactivex.Maybe
import io.reactivex.functions.Function
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class RxMaybeErrorCallAdapter constructor(private val wrapped: CallAdapter<Any, Maybe<*>>) : CallAdapter<Any, Maybe<*>> {
    override fun responseType(): Type {
        return wrapped.responseType()
    }

    @SuppressWarnings("unchecked")
    override fun adapt(call: Call<Any>): Maybe<*> {
        return wrapped.adapt(call)
                .onErrorResumeNext(Function { throwable ->
                    Maybe.error(MarvelServerExceptionFactory.create(throwable))
                })
    }
}