package com.lul.dulesum

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.DatePicker.OnDateChangedListener
import android.widget.ImageView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.lul.dulesum.databinding.FragmentHomeBinding
import io.paperdb.Paper
import java.util.*


private lateinit var binding: FragmentHomeBinding
private lateinit var database: DatabaseReference
private var dpd: DatePickerDialog? = null


class HomeFragment : Fragment(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dpd = DatePickerDialog(
            requireContext(),
            R.style.accent,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            },
            202, 0, 22

        )


        binding = FragmentHomeBinding.inflate(layoutInflater)
        database = Firebase.database.reference

        var countWeeks = 12

        binding.calendarButton.setOnClickListener(this)



        Paper.book("main").write("date", 0)
        var listAllItems = Paper.book("main").read<ArrayList<ItemSubject>>("subjects")
        var listEvenItems: ArrayList<ItemSubject> = arrayListOf()
        var listNotEvenItems: ArrayList<ItemSubject> = arrayListOf()
        var finalListDays: ArrayList<ItemOneDay> = arrayListOf()
        var evenListDays: ArrayList<ItemOneDay> = arrayListOf()
        var notEvenListDays: ArrayList<ItemOneDay> = arrayListOf()

        println(listAllItems?.size)
        if (listAllItems != null) {
            for (i in 0 until listAllItems.size) {
                var finalI = i + 1
                if (finalI % 2 == 0)
                    listEvenItems.add(listAllItems.get(i))
                else
                    listNotEvenItems.add(listAllItems.get(i))

            }
        }


        for (i in 0 until listEvenItems.size) {
            var finalI = (i + 1) % 4
            when (finalI) {
                1 -> {

                    var tempItem = ItemOneDay()
                    var tempItemS = ItemOneDay()
                    tempItem.FirstSubject = listEvenItems.get(i)
                    tempItemS.FirstSubject = listNotEvenItems.get(i)
                    tempItem.SecondSubject = listEvenItems.get(i + 1)
                    tempItemS.SecondSubject = listNotEvenItems.get(i + 1)
                    tempItem.ThirdSubject = listEvenItems.get(i + 2)
                    tempItemS.ThirdSubject = listNotEvenItems.get(i + 2)
                    tempItem.FourSubject = listEvenItems.get(i + 3)
                    tempItemS.FourSubject = listNotEvenItems.get(i + 3)
                    evenListDays.add(tempItem)
                    notEvenListDays.add(tempItemS)

                }
            }
        }
        evenListDays.add(ItemOneDay())
        notEvenListDays.add(ItemOneDay())

        var abbrSource = listAllItems?.get(0)?.SubjectGroup?.split(" ")
        var abbr = ""
        for (word in abbrSource!!) {
            abbr = abbr + word.get(0)

        }
        abbr = abbr.substring(0, abbr.length - 1)
            .uppercase(Locale.getDefault()) + " " + listAllItems?.get(0)?.SubjectCourse?.get(7) + "-" + abbr.get(
            abbr.length - 1
        )
        binding.groupTv.text = abbr
        for (i in 0 until countWeeks) {
            finalListDays.addAll(evenListDays)
            finalListDays.addAll(notEvenListDays)
        }



        val adapter = AdapterViewPager(
            finalListDays,
            requireContext()
        )
        binding.ViewPager2.adapter = adapter


        binding.ViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {


///86400000









                if ((position / 7) % 2 == 0)
                    binding.weekTv.text = "Нечетная"
                else
                    binding.weekTv.text = "Четная"

                super.onPageSelected(position)

            }

        })


        dpd?.datePicker?.setOnDateChangedListener(object : OnDateChangedListener {

            @SuppressLint("SetTextI18n")
            override fun onDateChanged(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {


                dpd?.dismiss()

            }
        })




        return binding.root
    }


    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.calendarButton -> {
                dpd?.show()
                dpd?.getButton(DatePickerDialog.BUTTON_POSITIVE)?.text = "Сегодня"
                dpd?.getButton(DatePickerDialog.BUTTON_POSITIVE)
                    ?.setOnClickListener(object : OnClickListener {
                        override fun onClick(p0: View?) {

                            dpd?.dismiss()
                        }

                    })

                dpd?.getButton(DatePickerDialog.BUTTON_NEGATIVE)?.visibility = View.GONE
            }
        }
    }

    fun setDot(dot: Int) {
        var dotList = arrayListOf<ImageView>(
            binding.dot1,
            binding.dot2,
            binding.dot3,
            binding.dot4,
            binding.dot5,
            binding.dot6,
            binding.dot7,

            )

        for (item in dotList) {
            item.foreground = resources.getDrawable(R.drawable.backgroundroundbluelight)
        }
        dotList.get(dot).foreground = resources.getDrawable(R.drawable.backgroundroundblue)

    }

}