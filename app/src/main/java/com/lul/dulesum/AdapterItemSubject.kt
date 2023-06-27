package com.lul.dulesum

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.lul.dulesum.AdapterItemSubject.MyViewHolder

class AdapterItemSubject(private val dataSet: ArrayList<SearchFragment.itemSearch>) :
    RecyclerView.Adapter<AdapterItemSubject.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val group: TextView
        val institute: TextView
        val week: TextView
        val time: TextView
        val title: TextView
        val itemConstraintLayout: LinearLayout

        init {

            group = view.findViewById(R.id.itemGroup)
            institute = view.findViewById(R.id.itemInstitute)
            week = view.findViewById(R.id.itemWeek)
            time = view.findViewById(R.id.itemTime)
            title = view.findViewById(R.id.itemTitle)
            itemConstraintLayout = view.findViewById(R.id.itemSubLL)
        }

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.subject_item,
            parent, false
        )

        return MyViewHolder(view)
    }

    fun getPosition(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataSet[position]
        holder.institute.text = currentItem.instituteName
        holder.group.text = currentItem.groupName
        holder.week.text = currentItem.week
        holder.time.text = currentItem.time
        holder.title.text = currentItem.title
    }
}



