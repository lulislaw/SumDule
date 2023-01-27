package com.lul.dulesum

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.lul.dulesum.databinding.FragmentNewsBinding
import org.jsoup.Jsoup
import org.jsoup.select.Elements

private lateinit var binding: FragmentNewsBinding
private lateinit var listNews: ArrayList<ItemNews>
private lateinit var adapterNews: AdapterNews
private lateinit var secThread: Thread
private lateinit var runnable: Runnable

class NewsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(layoutInflater)

        listNews = arrayListOf()



        init()









        return binding.root
    }

    companion object {

        fun newInstance() =
            NewsFragment()
    }

    fun getWeb() {
        listNews.clear()
        var docnews = Jsoup.connect("https://guu.ru/category/news_ru/").get()
        var elements: Elements = docnews.getElementsByClass("img-holder thumbnail pull-left")
        var elementsCF: Elements = docnews.getElementsByClass("post cf")


        for (i in 0 until elements.size) {
            var tempNews = ItemNews()
            tempNews.Title = elementsCF.get(i).select("h3").first()?.text().toString()
            tempNews.Date = elementsCF.get(i).select("small").get(1).text().toString()

            Glide.with(requireContext())
                .asBitmap()
                .load(elements.get(i).select("img").first()?.absUrl("src").toString())
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        tempNews.bitmapImage = resource
                        listNews.add(tempNews)
                        adapterNews = AdapterNews(listNews)
                        binding.recyclerNews.adapter = adapterNews
                        binding.recyclerNews.layoutManager =
                            StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
                        binding.progressBar.visibility = View.GONE

                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                })


        }


    }

    private fun init() {
        runnable = Runnable { getWeb() }
        secThread = Thread(runnable)
        secThread.start()
    }

}