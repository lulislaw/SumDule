package com.lul.dulesum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterNews(private val dataSet: ArrayList<ItemNews>) :
    RecyclerView.Adapter<AdapterNews.MyViewHolderNews>() {

    class MyViewHolderNews(view: View) : RecyclerView.ViewHolder(view) {
        val title : TextView
        val date : TextView
        val image : ImageView

        init{
            title = view.findViewById(R.id.newsTitle)
            date = view.findViewById(R.id.newsDate)
            image = view.findViewById(R.id.newsImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderNews {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_news,
            parent, false
        )

        return MyViewHolderNews(view)
    }

    override fun onBindViewHolder(holder: MyViewHolderNews, position: Int) {
       var currentItem = dataSet[position]

        holder.title.text = currentItem.Title
        holder.date.text = currentItem.Date
        holder.image.setImageBitmap(currentItem.bitmapImage)

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}