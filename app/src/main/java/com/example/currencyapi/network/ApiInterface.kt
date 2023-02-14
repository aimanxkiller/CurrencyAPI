package com.example.currencyapi.network

import com.example.currencyapi.model.CurrencyRates2
import com.example.currencyapi.model.WeatherAPI
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("myr")
    fun getData(): Call<CurrencyRates2>

    @GET("forecast?latitude=40.71&longitude=-74.01&hourly=temperature_2m,rain,showers,weathercode")
    fun getDataAPI(): Call<WeatherAPI>
}