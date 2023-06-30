package com.lul.dulesum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.lul.dulesum.databinding.FragmentCabinetBinding


class CabinetFragment : Fragment() {
    var guuUrl = "https://my.guu.ru/student"
    lateinit var binding: FragmentCabinetBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCabinetBinding.inflate(layoutInflater)
        var webView = binding.webView
        webView.loadUrl(guuUrl)
        webView.webViewClient = CustomWebViewClient()

        webView.settings.domStorageEnabled = true
        webView.settings.saveFormData = true
        webView.settings.savePassword = true
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.settings.userAgentString = "Chrome/41.0.2228.0 Safari/537.36"


        return binding.root
    }
    class CustomWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

    companion object {

        fun newInstance() =
            CabinetFragment()
    }

}