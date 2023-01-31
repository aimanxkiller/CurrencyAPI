package com.example.currencyapi

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("myr")
    fun getData(): Call<CurrencyRates2>
}