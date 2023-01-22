package com.lul.dulesum

import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.util.*


class AdapterViewPager(
    val list: ArrayList<ItemOneDay>,
    val context: Context

) : RecyclerView.Adapter<AdapterViewPager.ViewPagerViewHolder>() {
    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_viewpager, parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val dermoTime = context.resources.getStringArray(R.array.dermovoeTime)
        val curDay = list[position]
        holder.itemView.findViewById<TextView>(R.id.dayOfWeekTv).text =
            context.resources.getStringArray(R.array.daysOfWeek).get(position % 7)
        val Titles = arrayListOf<TextView>(
            holder.itemView.findViewById<TextView>(R.id.vpSubjectTitleOne),
            holder.itemView.findViewById<TextView>(R.id.vpSubjectTitleTwo),
            holder.itemView.findViewById<TextView>(R.id.vpSubjectTitleThree),
            holder.itemView.findViewById<TextView>(R.id.vpSubjectTitleFour),

            )
        val Rooms = arrayListOf<TextView>(
            holder.itemView.findViewById<TextView>(R.id.vpSubjectRoomOne),
            holder.itemView.findViewById<TextView>(R.id.vpSubjectRoomTwo),
            holder.itemView.findViewById<TextView>(R.id.vpSubjectRoomThree),
            holder.itemView.findViewById<TextView>(R.id.vpSubjectRoomFour),

            )
        val Teachers = arrayListOf<TextView>(
            holder.itemView.findViewById<TextView>(R.id.vpSubjectTeacherOne),
            holder.itemView.findViewById<TextView>(R.id.vpSubjectTeacherTwo),
            holder.itemView.findViewById<TextView>(R.id.vpSubjectTeacherThree),
            holder.itemView.findViewById<TextView>(R.id.vpSubjectTeacherFour),

            )
        val Times = arrayListOf<TextView>(
            holder.itemView.findViewById<TextView>(R.id.vpSubjectTimeOne),
            holder.itemView.findViewById<TextView>(R.id.vpSubjectTimeTwo),
            holder.itemView.findViewById<TextView>(R.id.vpSubjectTimeThree),
            holder.itemView.findViewById<TextView>(R.id.vpSubjectTimeFour),

            )
        val Types = arrayListOf<TextView>(
            holder.itemView.findViewById<TextView>(R.id.vpSubjectTypeOne),
            holder.itemView.findViewById<TextView>(R.id.vpSubjectTypeTwo),
            holder.itemView.findViewById<TextView>(R.id.vpSubjectTypeThree),
            holder.itemView.findViewById<TextView>(R.id.vpSubjectTypeFour),

            )
        val Blocks = arrayListOf<LinearLayout>(
            holder.itemView.findViewById<LinearLayout>(R.id.vpSubjectOne),
            holder.itemView.findViewById<LinearLayout>(R.id.vpSubjectTwo),
            holder.itemView.findViewById<LinearLayout>(R.id.vpSubjectThree),
            holder.itemView.findViewById<LinearLayout>(R.id.vpSubjectFour),

            )
        val Countdowns = arrayListOf<TextView>(
            holder.itemView.findViewById<TextView>(R.id.vpSubjectCountdownOne),
            holder.itemView.findViewById<TextView>(R.id.vpSubjectCountdownTwo),
            holder.itemView.findViewById<TextView>(R.id.vpSubjectCountdownThree),
            holder.itemView.findViewById<TextView>(R.id.vpSubjectCountdownFour),

            )
        val Subjects = arrayListOf(
            curDay.FirstSubject,
            curDay.SecondSubject,
            curDay.ThirdSubject,
            curDay.FourSubject

        )


        for (i in 0..3) {

            Blocks.get(i).visibility = View.VISIBLE
            Titles.get(i).text = Subjects.get(i)?.SubjectTitle?.trim()
            Teachers.get(i).text = Subjects.get(i)?.SubjectTeacher?.trim()
            Rooms.get(i).text = Subjects.get(i)?.SubjectRoom?.trim()
            Types.get(i).text = Subjects.get(i)?.SubjectType?.trim()
            Times.get(i).text = Subjects.get(i)?.SubjectTime?.trim()
            if (Subjects.get(i)?.SubjectTitle?.trim()?.length!! <= 3) {
                Blocks.get(i).visibility = View.GONE
            }


        }

        val timer = object : CountDownTimer(360000, 5000) {
            override fun onTick(p0: Long) {


                for (subjectId in 0..3) {
                    for (dermo in dermoTime) {




                    }

                }

            }

            override fun onFinish() {
                start()
            }

        }.start()


    }

    override fun getItemCount(): Int {

        return list.size
    }

}