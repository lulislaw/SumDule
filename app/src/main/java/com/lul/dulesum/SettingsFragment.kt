package com.lul.dulesum

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.lul.dulesum.databinding.FragmentSettingsBinding
import io.paperdb.Paper


private lateinit var database: DatabaseReference
private lateinit var binding: FragmentSettingsBinding
private lateinit var listInstitute: ArrayList<String>
private lateinit var listGroups: ArrayList<String>
private lateinit var listSubjects: ArrayList<ItemSubject>

class SettingsFragment : Fragment(),View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        database = Firebase.database.reference
        binding.sgSpinnerInstitute.isEnabled = false
        val query = database.limitToFirst(999)
        listInstitute = arrayListOf()
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childSnapshot in dataSnapshot.children) {
                    val key = childSnapshot.key
                    // Вы можете выполнить нужные действия с полученным ключом (key) здесь
                    listInstitute.add(key.toString())
                }
                listInstitute.remove("3 курс")
                var sgArrayAdapterInstitute = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    listInstitute
                )
                sgArrayAdapterInstitute.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.sgSpinnerInstitute.adapter = sgArrayAdapterInstitute
                binding.sgSpinnerInstitute.isEnabled = true
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        listGroups = arrayListOf()
        listSubjects = arrayListOf()
        binding.sgButtonSave.setOnClickListener(this)
        binding.sgSpinnerGroup.isEnabled = false
        binding.sgButtonSave.isEnabled = false
        binding.sgProgressBar.visibility = View.VISIBLE

        binding.sgSpinnerInstitute.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    binding.sgSpinnerGroup.isEnabled = false
                    binding.sgButtonSave.isEnabled = false
                    binding.sgProgressBar.visibility = View.VISIBLE
                    database
                        .child(
                            listInstitute.get(p2)
                        ).get().addOnSuccessListener {
                            listGroups.clear()
                            for (child in it.children) {
                                println(child.key)
                                listGroups.add(child.key.toString())
                            }
                            var sgArrayAdapterGroup = ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_item,
                                listGroups
                            )
                            sgArrayAdapterGroup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            binding.sgSpinnerGroup.adapter = sgArrayAdapterGroup
                            binding.sgSpinnerGroup.isEnabled = true


                        }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }

        binding.sgSpinnerGroup.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    binding.sgButtonSave.isEnabled = false
                    binding.sgProgressBar.visibility = View.VISIBLE
                    listSubjects.clear()
                    for (i in 0..47) {
                        var subject: ItemSubject = ItemSubject()
                        database
                            .child(listInstitute.get(binding.sgSpinnerInstitute.selectedItemId.toInt()))
                            .child(listGroups.get(p2)).child("lesson_$i").get().addOnSuccessListener {
                                for (part in it.children) {
                                    when (part.key.toString()) {
                                        "time" -> subject.SubjectTime = part.value.toString()
                                        "text" -> subject.SubjectText = part.value.toString()
                                    }
                                }
                                listSubjects.add(subject)
                                if (listSubjects.size > 46) {

                                    binding.sgButtonSave.isEnabled = true
                                    binding.sgProgressBar.visibility = View.INVISIBLE
                                }

                            }

                    }

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
                R.id.sgButtonSave -> {
                    Paper.book("main").write("subjects", listSubjects)
                    Paper.book("main").write("group", binding.sgSpinnerGroup.selectedItem)
                }
        }
    }


}