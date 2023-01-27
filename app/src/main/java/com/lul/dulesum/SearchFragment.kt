package com.lul.dulesum

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.FileAsyncHttpResponseHandler
import com.lul.dulesum.databinding.FragmentSearchBinding
import cz.msebera.android.httpclient.Header
import java.io.File
import java.io.FileReader
import java.nio.charset.Charset


private lateinit var binding: FragmentSearchBinding
private lateinit var adapterItemSubject: AdapterItemSubject
lateinit var subjectList: ArrayList<ItemSubject>
private var newTopMargin = 40
private var searchAtTop = false
lateinit var dialog: Dialog
lateinit var viewDialog: View
var params: ViewGroup.MarginLayoutParams? = null
lateinit var listDir: Array<String>

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
        dialog = Dialog(requireContext(), R.style.accent)
        viewDialog = layoutInflater.inflate(R.layout.popup_filter,null)

        dialog.setContentView(viewDialog)




        binding.searchEditText.isEnabled = false
        val client = AsyncHttpClient()
        listDir = arrayOf()
        client.get(
            "https://github.com/lulislaw/SUMTable_sourceXLSX/blob/main/search.txt?raw=true",
            object :
                FileAsyncHttpResponseHandler(requireContext()) {


                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, file: File?) {
                    var tfr = FileReader(file)
                    var text = "";
                    val buffer = CharArray(8096)
                    var chars: Int = tfr.read(buffer)
                    while (chars != -1) {
                        text = text + String(buffer, 0, chars)
                        chars = tfr.read(buffer)
                    }
                    text = String(text.toByteArray(), Charset.forName("UTF-8"))
                    listDir = text.split("Y").toTypedArray()


                    params?.topMargin = newTopMargin
                    binding.searchTv.setLayoutParams(params)
                    search()
                    binding.searchProgressBarPb.visibility = View.GONE
                    binding.searchEditText.isEnabled = true
                }


                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    throwable: Throwable?,
                    file: File?
                ) {

                }

            })

        subjectList = arrayListOf()

        params = binding.searchTv.getLayoutParams() as ViewGroup.MarginLayoutParams

        binding.searchEditText.setOnClickListener(this)
        binding.searchFilterButton.setOnClickListener(this)




        binding.searchEditText.setHorizontallyScrolling(false)
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                search()
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

            R.id.searchFilterButton -> {
                dialog.show()
            }
            R.id.searchTv ->
            {


            }


        }
    }


    fun search() {


        var tempList: ArrayList<ItemSubject> = arrayListOf()

        for (it in listDir) {

            if (it.split("s.").size > 1) {
                if (it.toLowerCase()
                        .contains(binding.searchEditText.text.toString().toLowerCase())
                ) {
                    var tempItem = ItemSubject()
                    tempItem.SubjectCourse = "#curs"
                    tempItem.SubjectGroup = "#grup"
                    tempItem.SubjectInstitute = "#inst"
                    tempItem.SubjectRoom = it.split("r.")[1].trim()
                    tempItem.SubjectWeek = it.split("week.")[1].trim()
                    tempItem.SubjectType = it.split("ty.")[1].trim()
                    tempItem.SubjectTime = it.split("time.")[1].trim()
                    tempItem.SubjectTeacher = it.split("te.")[1].trim()
                    tempItem.SubjectTitle = it.split("s.")[1].trim()
                    tempList.add(tempItem)


                }
            }
        }
        adapterItemSubject = AdapterItemSubject(tempList)
        binding.searchRecycler.adapter = adapterItemSubject
        binding.searchRecycler.layoutManager =
            StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)


    }
}

