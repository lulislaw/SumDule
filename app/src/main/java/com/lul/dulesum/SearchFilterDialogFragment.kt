package com.lul.dulesum

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lul.dulesum.databinding.FragmentSearchFilterDialogBinding

class SearchFilterDialogFragment : BottomSheetDialogFragment() {

    private var binding: FragmentSearchFilterDialogBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchFilterDialogBinding.inflate(layoutInflater, container, false)
        this.binding = binding

        setButtons(true)

        binding.applyButton.setOnClickListener {
            val type = binding.type.text.trim().toString()
            val course = binding.course.text.trim().toString()
            val time = binding.time.text.trim().toString()
            val day = binding.day.text.trim().toString()
            val week = binding.week.text.trim().toString()

            setFragmentResult(
                APPLY_REQUEST_KEY,
                bundleOf(
                    "type" to type,
                    "course" to course,
                    "time" to time,
                    "day" to day,
                    "week" to week
                )
            )

            dismiss()
        }

        return binding.root
    }

    private fun setButtons(isFirstTime: Boolean) {
        val types = resources.getStringArray(R.array.type)
        val courses = resources.getStringArray(R.array.course)
        val times = resources.getStringArray(R.array.times)
        val days = resources.getStringArray(R.array.day)
        val weeks = resources.getStringArray(R.array.week)

        val typesAdapter = ArrayAdapter(requireContext(), R.layout.item_exposed_dropdown_menu, types)
        val coursesAdapter = ArrayAdapter(requireContext(), R.layout.item_exposed_dropdown_menu, courses)
        val timesAdapter = ArrayAdapter(requireContext(), R.layout.item_exposed_dropdown_menu, times)
        val daysAdapter = ArrayAdapter(requireContext(), R.layout.item_exposed_dropdown_menu, days)
        val weeksAdapter = ArrayAdapter(requireContext(), R.layout.item_exposed_dropdown_menu, weeks)

        binding?.let {
            with(it) {
                type.apply {
                    setDropDownBackgroundDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.background_white_8dp
                        )
                    )
                    if (isFirstTime) setText(types.first())
                    setAdapter(typesAdapter)
                }
                course.apply {
                    setDropDownBackgroundDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.background_white_8dp
                        )
                    )
                    if (isFirstTime) setText(courses.first())
                    setAdapter(coursesAdapter)
                }
                time.apply {
                    setDropDownBackgroundDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.background_white_8dp
                        )
                    )
                    if (isFirstTime) setText(times.first())
                    setAdapter(timesAdapter)
                }
                day.apply {
                    setDropDownBackgroundDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.background_white_8dp
                        )
                    )
                    if (isFirstTime) setText(days.first())
                    setAdapter(daysAdapter)
                }
                week.apply {
                    setDropDownBackgroundDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.background_white_8dp
                        )
                    )
                    if (isFirstTime) setText(weeks.first())
                    setAdapter(weeksAdapter)
                }
            }
        }
    }

    companion object {
        const val APPLY_REQUEST_KEY = "applyFiltersKey"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onResume() {
        super.onResume()
        setButtons(isFirstTime = false)
    }

}