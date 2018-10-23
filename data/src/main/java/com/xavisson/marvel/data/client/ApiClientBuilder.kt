package com.xavisson.marvel.data.client

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.xavisson.marvel.data.adapter.ErrorCallAdapterFactory
import com.xavisson.marvel.data.adapter.RxErrorCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass

class ApiClientBuilder(apiClient: MarvelApiClient, converterFactoryParam: GsonConverterFactory? = null) {
    companion object {
        private const val BASE_URL: String = "https://gateway.marvel.com"
    }

    private val gsonBuilder: Gson = GsonBuilder()
            .create()
    private val converterFactory = converterFactoryParam ?: GsonConverterFactory.create(gsonBuilder)
    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(apiClient.getClient())
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(RxErrorCallAdapterFactory.create())
            .addCallAdapterFactory(ErrorCallAdapterFactory.create())
            .build()

    fun <T> buildEndpoint(apiClass: Class<T>): T {
        return retrofit.create(apiClass)
    }

    fun <T : Any> buildEndpoint(apiClass: KClass<T>): T {
        return retrofit.create(apiClass.java)
    }
}