package com.example.currencyapi

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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
        Log.e("Test", userList.size.toString())
        holder.userID.text = splitter[0]
        holder.title.text = splitter[1]
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
