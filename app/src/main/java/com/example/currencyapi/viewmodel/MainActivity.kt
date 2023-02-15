package com.example.currencyapi.viewmodel

import android.annotation.SuppressLint
import android.graphics.Color
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
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
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
    lateinit var graph:GraphView

    private val BASE_URL = "https://open.er-api.com/v6/latest/"
    private val BASE_URL2 = "https://api.open-meteo.com/v1/"
    lateinit var data: CurrencyRates2
    lateinit var data2: WeatherAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Graph layout
        graph = findViewById(R.id.graphOne)

        recyclerView = findViewById(R.id.recyclerviewCur)
        recyclerView.setHasFixedSize(true)
        linearlayoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
        recyclerView.layoutManager = linearlayoutManager

        getCurrencyData()
        getWeatherData()
    }

    private fun getWeatherData(){
        val okhttpClientBuilder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        okhttpClientBuilder.addInterceptor(logging)
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL2)
            .client(okhttpClientBuilder.build())
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getDataAPI()

        retrofitData.enqueue(object : Callback<WeatherAPI>{
            override fun onResponse(call: Call<WeatherAPI>, response: Response<WeatherAPI>) {
                data2 = response.body()!!

                //Form Series
                val formOne = LineGraphSeries<DataPoint>()
                formOne.color = Color.RED
                val formTwo = LineGraphSeries<DataPoint>()
                var y:Double
                graph.viewport.setMaxX(70.0)
                for (x in 0 .. 150){
                    val z:Double = x.toDouble()
                    y= data2.hourly.weathercode[x].toDouble()
                    formOne.appendData(DataPoint(z,y),true,200)
                    y= data2.hourly.rain[x]
                    formTwo.appendData(DataPoint(z,y),true,200)
                }

                graph.addSeries(formOne)
                graph.addSeries(formTwo)

            }
            override fun onFailure(call: Call<WeatherAPI>, t: Throwable) {
                Log.e("DataError",t.message.toString())
                Toast.makeText(this@MainActivity,t.message,Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getCurrencyData(){
        val okhttpClientBuilder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        okhttpClientBuilder.addInterceptor(logging)
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okhttpClientBuilder.build())
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<CurrencyRates2>{
            @SuppressLint("NotifyDataSetChanged")
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