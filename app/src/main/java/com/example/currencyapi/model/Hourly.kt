package com.example.currencyapi.model

data class Hourly(
    val rain: List<Double>,
    val showers: List<Double>,
    val temperature_2m: List<Double>,
    val time: List<String>,
    val weathercode: List<Int>
)
