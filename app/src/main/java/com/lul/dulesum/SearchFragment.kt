package com.lul.dulesum

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.lul.dulesum.databinding.FragmentSearchBinding
import cz.msebera.android.httpclient.Header
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : Fragment(), OnClickListener {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapterItemSubject: AdapterItemSubject
    lateinit var subjectList: ArrayList<ItemSubject>
    private var newTopMargin = 26
    private var searchAtTop = false
    var params: ViewGroup.MarginLayoutParams? = null
    lateinit var listDir: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        binding.searchTv.setOnClickListener(this)
        binding.searchEditText.isEnabled = false
        val client = AsyncHttpClient()
        listDir = arrayOf()
        client["https://github.com/lulislaw/SUMTable_sourceXLSX/blob/main/search.txt?raw=true", object :
            AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                var source = responseBody?.let { String(it, Charsets.UTF_8) }
                if (source != null) {
                    listDir = source.split("Y").toTypedArray()
                }
                binding.searchEditText.isEnabled = true

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {

            }

        }]

        subjectList = arrayListOf(ItemSubject())
        binding.searchRecycler.adapter = AdapterItemSubject(subjectList)
        params = binding.searchTv.getLayoutParams() as ViewGroup.MarginLayoutParams
        binding.searchRecycler.layoutManager =
            StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)





        binding.searchEditText.setHorizontallyScrolling(false)
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!searchAtTop) {
                    val a: Animation = object : Animation() {
                        override fun applyTransformation(
                            interpolatedTime: Float,
                            t: Transformation?
                        ) {
                            params?.topMargin =
                                params?.topMargin?.minus(((newTopMargin * interpolatedTime).toInt()))
                            binding.searchTv.setLayoutParams(params)
                        }
                    }
                    a.duration = 500
                    binding.searchTv.startAnimation(a)
                    searchAtTop = true


                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


                var tempList: ArrayList<ItemSubject> = arrayListOf()

                for (it in listDir) {

                    if (it.toLowerCase().contains(binding.searchEditText.text.toString().toLowerCase())) {
                        var tempItem = ItemSubject()
                        tempItem.SubjectCourse = "#curs"
                        tempItem.SubjectGroup = "#grup"
                        tempItem.SubjectInstitute = "#inst"
                        tempItem.SubjectRoom = it.split("r.")[0] + " "
                        tempItem.SubjectWeek = it.split("week.")[0] + " "
                        tempItem.SubjectType = it.split("ty.")[0] + " "
                        tempItem.SubjectTime = it.split("time.")[0] + " "
                        tempItem.SubjectTeacher = it.split("te.")[0] + " "
                        tempItem.SubjectTitle = it.split("s.")[0] + " "
                        tempList.add(tempItem)


                    }
                }

                adapterItemSubject = AdapterItemSubject(tempList)
                binding.searchRecycler.adapter = adapterItemSubject
                binding.searchRecycler.layoutManager =
                    StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)

            }

            override fun afterTextChanged(p0: Editable?) {



            }

        })




        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SearchFragment()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.searchTv -> {


            }


        }
    }


}

