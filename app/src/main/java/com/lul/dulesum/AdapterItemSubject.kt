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

class AdapterItemSubject(private val dataSet: ArrayList<ItemSubject>) :
    RecyclerView.Adapter<AdapterItemSubject.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val course : TextView
        val group : TextView
        val institute : TextView
        val room : TextView
        val week : TextView
        val type : TextView
        val time : TextView
        val teacher : TextView
        val title : TextView
        val itemConstraintLayout: LinearLayout
        init {
            course = view.findViewById(R.id.itemCourse)
            group = view.findViewById(R.id.itemGroup)
            institute = view.findViewById(R.id.itemInstitute)
            room = view.findViewById(R.id.itemRoom)
            week = view.findViewById(R.id.itemWeek)
            type = view.findViewById(R.id.itemType)
            time = view.findViewById(R.id.itemTime)
            teacher = view.findViewById(R.id.itemTeacher)
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


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataSet[position]
        holder.course.text = currentItem.SubjectCourse
        holder.group.text = currentItem.SubjectGroup
        holder.institute.text = currentItem.SubjectInstitute
        holder.room.text = currentItem.SubjectRoom
        holder.week.text = currentItem.SubjectWeek
        holder.type.text = currentItem.SubjectType
        holder.time.text = currentItem.SubjectTime
        holder.teacher.text = currentItem.SubjectTeacher
        holder.title.text = currentItem.SubjectTitle
        println("fdsfasdfasdgasdgdasfgdsfg")

    }
}



