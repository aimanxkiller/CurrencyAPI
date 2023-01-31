package com.example.currencyapi

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MyAdapter (private val context:Context, private val userList:CurrencyRates2):RecyclerView.Adapter<MyAdapter.ViewHolder>(){
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var userID:TextView
        var title:TextView

        init {
            userID = itemView.findViewById(R.id.textID)
            title =  itemView.findViewById(R.id.textTitle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.rows_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var string = userList.rates.toString()
            .replace("(","")
            .replace(")","")
            .split(",")

        string.forEachIndexed { index, s ->
            val splitter = string[index].split("=")
            Log.e("DataSplit",   "RM 1 value is " + splitter[1] +splitter[0])
            holder.userID.text = splitter[0]
            holder.title.text = splitter[1]
        }

    }

    override fun getItemCount(): Int {
        var string = userList.rates.toString()
            .replace("(","")
            .replace(")","")
            .split(",")

        return string.size
    }

}
