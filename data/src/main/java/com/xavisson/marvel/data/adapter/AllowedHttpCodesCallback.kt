package com.xavisson.marvel.data.adapter

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface AllowedHttpCodesCallback<T> : Callback<T> {
    fun onAllowedHttpCode(call: Call<T>, response: Response<T>)
    fun exceptionalCodes(): List<Int>
}