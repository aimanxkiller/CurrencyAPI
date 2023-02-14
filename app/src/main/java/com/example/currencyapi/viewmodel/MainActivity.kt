package com.example.currencyapi.viewmodel

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapi.R
import com.example.currencyapi.adapter.MyAdapter
import com.example.currencyapi.model.CurrencyRates2
import com.example.currencyapi.model.WeatherAPI
import com.example.currencyapi.network.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var myAdapter: MyAdapter
    private lateinit var linearlayoutManager: LinearLayoutManager
    lateinit var recyclerView: RecyclerView

    private val BASE_URL = "https://open.er-api.com/v6/latest/"
    lateinit var data: CurrencyRates2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerviewCur)
        recyclerView.setHasFixedSize(true)
        linearlayoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView.layoutManager = linearlayoutManager

        var x:WeatherAPI

        getCurrencyData()
//        getWeatherData()
    }

    private fun getWeatherData(){
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

        retrofitData.enqueue(object : Callback<WeatherAPI>{
            override fun onResponse(call: Call<WeatherAPI>, response: Response<WeatherAPI>) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<WeatherAPI>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun getCurrencyData(){
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

                val string:Array<String> = data.rates.toString()
                    .replace("(","")
                    .replace(")","")
                    .replace("[","")
                    .replace("]","")
                    .replace("RatesX","")
                    .split(",").toTypedArray()

                myAdapter = MyAdapter(baseContext,string)
                myAdapter.notifyDataSetChanged()
                recyclerView.adapter = myAdapter

            }
            override fun onFailure(call: Call<CurrencyRates2>, t: Throwable) {
                Log.e("DataError",t.message.toString())
                Toast.makeText(this@MainActivity,t.message,Toast.LENGTH_SHORT).show()
            }
        })
    }
}