package com.example.newsappbykotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.FragmentActivity
import com.example.newsappbykotlin.dyi.WebBar


class DisplayNews : FragmentActivity() {
    private lateinit var mWebView:WebView
    private lateinit var mWebBar:WebBar
    private var mUrl:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_news)
        initView()
        initEvent()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView(){
        mWebView = findViewById(R.id.adn_content_wb)
        mWebBar = findViewById(R.id.adn_toolbar_wb)
        mUrl = this.intent.getStringExtra("url")
        mWebView.loadUrl(mUrl)
        mWebView.settings.javaScriptEnabled = true
        mWebView.webViewClient = WebViewClient()
    }

    private fun initEvent(){

    }
}