package com.lul.dulesum

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.loopj.android.http.AsyncHttpClient
import com.lul.dulesum.databinding.FragmentSearchBinding
import java.nio.charset.Charset


private lateinit var binding: FragmentSearchBinding
private lateinit var adapterItemSubject: AdapterItemSubject
lateinit var subjectList: ArrayList<SearchFragment.itemSearch>
private var newTopMargin = 40
private var searchAtTop = false
//lateinit var dialog: Dialog
lateinit var viewDialog: View
var params: ViewGroup.MarginLayoutParams? = null
lateinit var listDir: Array<String>
var basicList = arrayListOf<SearchFragment.itemSearch>()

class SearchFragment : Fragment(), OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        binding.searchTv.setOnClickListener(this)
//        dialog = Dialog(requireContext(), R.style.accent)
        viewDialog = layoutInflater.inflate(R.layout.popup_filter, null)

        getAllObjectsWithParentKeys { array ->
            params?.topMargin = newTopMargin
            binding.searchTv.setLayoutParams(params)
            binding.searchProgressBarPb.visibility = View.GONE
            binding.searchEditText.isEnabled = true
            basicList = array
            adapterItemSubject = AdapterItemSubject(basicList)
            binding.searchRecycler.adapter = adapterItemSubject
            binding.searchRecycler.layoutManager =
                StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)



        }
//        dialog.setContentView(viewDialog)

        val searchFilterDialogFragment = SearchFilterDialogFragment()
        binding.searchFilterButton.setOnClickListener {
            searchFilterDialogFragment.show(parentFragmentManager, null)
        }

        binding.searchEditText.isEnabled = false


        subjectList = arrayListOf()

        params = binding.searchTv.getLayoutParams() as ViewGroup.MarginLayoutParams

        binding.searchEditText.setOnClickListener(this)
//        binding.searchFilterButton.setOnClickListener(this)
        binding.searchClearButton.setOnClickListener(this)




        val handler = Handler(Looper.getMainLooper())
        binding.searchEditText.setHorizontallyScrolling(false)
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                handler.postDelayed({
                    adapterItemSubject = AdapterItemSubject(searchItems(basicList, p0.toString().split(' ')) as ArrayList<itemSearch>)
                    binding.searchRecycler.adapter = adapterItemSubject
                    binding.searchRecycler.layoutManager =
                        StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
                }, 1000)

            }

        })

        // Вот твои данные, хавай
        setFragmentResultListener(APPLY_REQUEST_KEY) { _, bundle ->
            val course = bundle.getString("course")
            val type = bundle.getString("type")
            val time = bundle.getString("time")
            val day = bundle.getString("day")
            val week = bundle.getString("week")

            Toast.makeText(requireContext(), "$course, $type, $time, $day, $week", Toast.LENGTH_SHORT).show()
        }


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment()

        const val APPLY_REQUEST_KEY = "applyFiltersKey"
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.searchFilterButton -> {
//                dialog.show()
            }
            R.id.searchTv -> {


            }
            R.id.searchClearButton ->{
                binding.searchEditText.setText("")
            }


        }
    }



    data class itemSearch(
        var instituteName: String = "",
        var groupName: String = "",
        var title: String = "",
        var week: String = "",
        var time: String = ""
    )

    fun searchItems(
        items: ArrayList<itemSearch>,
        searchStrings: List<String>
    ): List<itemSearch> {
        return items.filter { item ->
            searchStrings.all { searchString ->
                item.instituteName.contains(searchString, ignoreCase = true) ||
                        item.groupName.contains(searchString, ignoreCase = true) ||
                        item.title.contains(searchString, ignoreCase = true) ||
                        item.week.contains(searchString, ignoreCase = true) ||
                        item.time.contains(searchString, ignoreCase = true)
            }
        }
    }

    fun getAllObjectsWithParentKeys(completion: (ArrayList<itemSearch>) -> Unit) {
        val ref = Firebase.database.reference
        val pairs = arrayListOf<itemSearch>()
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (institute in dataSnapshot.children) {
                    for (group in institute.children) {
                        for (lesson in group.children) {
                            var item: itemSearch = itemSearch()
                            for (value in lesson.children) {

                                item.instituteName = institute.key.toString().trim()
                                item.groupName = group.key.toString().replace('\n',' ')
                                when (value.key) {
                                    "time" -> item.time = value.value.toString()
                                    "week" -> item.week = value.value.toString()
                                    "text" -> item.title = value.value.toString().replace("(","\n(")
                                }
                            }
                            if (item.title != "None") {
                                pairs.add(item)
                            }
                        }
                    }

                }
                completion(pairs) // Вызываем колбэк и передаем заполненный список пар ключей-значений
            }

            override fun onCancelled(error: DatabaseError) {
                // Обработка ошибки при чтении данных
                completion(pairs) // Вызываем колбэк и передаем пустой список пар ключей-значений или сообщение об ошибке
            }
        })
    }


}

