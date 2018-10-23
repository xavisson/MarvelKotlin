package com.xavisson.marvel.data.adapter

import com.xavisson.marvel.data.exception.MarvelServerExceptionFactory
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ErrorCallAdapter(private val delegate: Call<Any>) : Call<Any> {
    override fun cancel() {
        delegate.cancel()
    }

    override fun execute(): Response<Any> {
        return delegate.execute()
    }

    override fun isExecuted(): Boolean {
        return delegate.isExecuted
    }

    override fun request(): Request {
        return delegate.request()
    }

    override fun isCanceled(): Boolean {
        return delegate.isCanceled
    }

    override fun enqueue(callback: Callback<Any>) {
        if (callback is AllowedHttpCodesCallback) {
            delegate.enqueue(AllowedHttpCodesExecutorCallback(callback))
        } else {
            delegate.enqueue(BasicExecutorCallback(callback))
        }
    }

    override fun clone(): Call<Any> {
        return ErrorCallAdapter(delegate.clone())
    }

    private open inner class BasicExecutorCallback(private val delegate: Callback<Any>) : Callback<Any> {
        override fun onResponse(call: Call<Any>, response: Response<Any>) {
            if (response.isSuccessful) {
                delegate.onResponse(call, response)
            } else {
                delegate.onFailure(call, MarvelServerExceptionFactory.create(HttpException(response)))
            }
        }

        override fun onFailure(call: Call<Any>, throwable: Throwable) {
            delegate.onFailure(call, MarvelServerExceptionFactory.create(throwable))
        }
    }

    private inner class AllowedHttpCodesExecutorCallback(private val delegate: AllowedHttpCodesCallback<Any>) : BasicExecutorCallback(delegate) {
        override fun onResponse(call: Call<Any>, response: Response<Any>) {
            super.onResponse(call, response)
            if (response.mustProcessError()) {
                delegate.onAllowedHttpCode(call, response)
            }
        }

        private fun <T> Response<T>.mustProcessError(): Boolean {
            return delegate.exceptionalCodes().contains(code())
        }
    }
}