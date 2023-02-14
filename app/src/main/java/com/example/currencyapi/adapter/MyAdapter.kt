package com.example.currencyapi.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapi.R
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToLong

class MyAdapter (val context:Context, val userList:Array<String>):RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userID: TextView
        var title: TextView
        init {
            userID = itemView.findViewById(R.id.textID)
            title = itemView.findViewById(R.id.textTitle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context.applicationContext)
            .inflate(R.layout.rows_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val splitter: List<String> = userList[position].split("=")
        holder.userID.text = splitter[0]
        val df = DecimalFormat("#.####")
        df.roundingMode= RoundingMode.DOWN

        holder.title.text = df.format(splitter[1].toDouble()*100).toString()
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}
