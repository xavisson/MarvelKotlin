package com.xavisson.marvel.data.adapter

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType
import java.util.Arrays

class ErrorCallAdapterFactory : CallAdapter.Factory() {
    companion object {
        fun create(): ErrorCallAdapterFactory {
            return ErrorCallAdapterFactory()
        }
    }

    override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit): CallAdapter<Any, Any>? {
        val typeClass = getRawType(returnType)
        if (typeClass != Call::class.java) {
            return null
        }
        val type = getResponseType(returnType)
        return ExecutorCallback(type)
    }

    private inner class ExecutorCallback(private val responseType: Type) : CallAdapter<Any, Any> {
        override fun responseType(): Type {
            return responseType
        }

        override fun adapt(call: Call<Any>): Call<Any> {
            return ErrorCallAdapter(call)
        }
    }

    private fun getResponseType(returnType: Type): Type {
        if (returnType !is ParameterizedType) {
            throw IllegalArgumentException("Call return type must be parameterized as Call<Foo> or Call<? extends Foo>")
        }
        val responseType = getSingleParameterUpperBound(returnType)
        if (CallAdapter.Factory.getRawType(responseType) == Response::class) {
            throw IllegalArgumentException("Call<T> cannot use Response as its generic parameter. Specify the response body type only (e.g., Call<TweetResponse>).")
        }
        return responseType
    }

    fun getSingleParameterUpperBound(type: ParameterizedType): Type {
        val types = type.actualTypeArguments
        if (types.size != 1) {
            throw IllegalArgumentException(
                    "Expected one type argument but got: " + Arrays.toString(types))
        }
        val paramType = types[0]
        if (paramType is WildcardType) {
            return paramType.upperBounds[0]
        }
        return paramType
    }
}