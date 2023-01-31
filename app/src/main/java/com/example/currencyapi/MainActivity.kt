package com.example.currencyapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val BASE_URL = "https://open.er-api.com/v6/latest/"
    lateinit var data:CurrencyRates2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCurrencyData()
    }

    private fun getCurrencyData(){
        val gson = GsonBuilder().setLenient().create()
        val okhttpClientBuilder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        okhttpClientBuilder.addInterceptor(logging)

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okhttpClientBuilder.build())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<CurrencyRates2>{
            override fun onResponse(call: Call<CurrencyRates2>, response: Response<CurrencyRates2>) {
                data = response.body()!!

                val string = data.rates.toString()
                    .replace("(","")
                    .replace(")","")
                    .split(",")

                string.forEachIndexed { index, s ->
                    val splitter = string[index].split("=")
                        Log.e("DataSplit",   "RM 1 value is " + splitter[1] +splitter[0])
                }

            }

            override fun onFailure(call: Call<CurrencyRates2>, t: Throwable) {
                Log.e("DataError",t.message.toString())
                Toast.makeText(this@MainActivity,t.message,Toast.LENGTH_SHORT).show()
            }

        })
    }
}