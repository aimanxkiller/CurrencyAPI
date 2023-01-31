package com.example.currencyapi

data class CurrencyRates2(
    val base_code: String,
    val provider: String,
    val rates: RatesX,
    val time_last_update_unix: Int
)

