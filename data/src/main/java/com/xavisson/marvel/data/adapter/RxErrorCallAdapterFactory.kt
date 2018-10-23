package com.xavisson.marvel.data.adapter

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type

class RxErrorCallAdapterFactory(private var rxAdapter: RxJava2CallAdapterFactory) : CallAdapter.Factory() {
    companion object {
        fun create(): CallAdapter.Factory {
            return RxErrorCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit): CallAdapter<Any, Any>? {
        val originalAdapter = rxAdapter.get(returnType, annotations, retrofit)
        val type = getRawType(returnType)
        if (type == Observable::class.java) {
            originalAdapter?.let {
                return RxObservableErrorCallAdapter(
                        originalAdapter as CallAdapter<Any, Observable<*>>
                ) as CallAdapter<Any, Any>
            }
        } else if (type == Single::class.java) {
            originalAdapter?.let {
                return RxSingleErrorCallAdapter(
                        originalAdapter as CallAdapter<Any, Single<*>>
                ) as CallAdapter<Any, Any>
            }
        } else if (type == Completable::class.java) {
            originalAdapter?.let {
                return RxCompletableErrorCallAdapter(
                        originalAdapter as CallAdapter<Any, Completable>
                ) as CallAdapter<Any, Any>
            }
        } else if (type == Maybe::class.java) {
            originalAdapter?.let {
                return RxMaybeErrorCallAdapter(
                        originalAdapter as CallAdapter<Any, Maybe<*>>
                ) as CallAdapter<Any, Any>
            }
        } else if (type == Flowable::class.java) {
            originalAdapter?.let {
                return RxFlowableErrorCallAdapter(
                        originalAdapter as CallAdapter<Any, Flowable<*>>
                ) as CallAdapter<Any, Any>
            }
        }
        return null
    }
}