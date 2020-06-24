package com.example.newsappbykotlin

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AppComponentFactory
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappbykotlin.adapter.search.SearchAdapter
import com.example.newsappbykotlin.bean.Bean
import com.example.newsappbykotlin.bean.searchbean.SearchBean
import com.example.newsappbykotlin.bean.searchbean.SearchList
import com.example.newsappbykotlin.item.SearchItem
import com.example.newsappbykotlin.utils.EndlessRecyclerOnScrollListener
import com.example.newsappbykotlin.utils.HttpParser
import com.example.newsappbykotlin.utils.HttpUtils
import com.example.newsappbykotlin.utils.ItemOnClickListener

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SearchResult : Activity() {
    private var mSearchList: MutableList<SearchItem> = mutableListOf()
    private lateinit var mSearchRecycle: RecyclerView
    private lateinit var mSearchAdapter: SearchAdapter
    private lateinit var mKeyWord:String
    private var mStartIndex:Int = 0
    private var mRefreshNum:Int = 10
    private var mSearchDataBuffer = mutableListOf<SearchItem>()

    private var mHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if(msg.what== Bean.SEARCHBEAN){
                val bean: SearchBean = msg.obj as SearchBean
                val n:Int = bean.result.num.toInt()
                for(i in 0 until n-1){
                    mSearchDataBuffer.add(SearchItem(bean.result.list[i]))
                }
                getData()
                mSearchAdapter.setLoadState(mSearchAdapter.LOADING_COMPLETE)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        initView()
        initData()
        initEvent()
    }

    private fun initView(){
        mSearchRecycle = findViewById(R.id.asr_content_rv)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mSearchRecycle.layoutManager = layoutManager
        mSearchAdapter = SearchAdapter(mSearchList)
        mSearchRecycle.adapter = mSearchAdapter
    }

    private fun initData(){
        mKeyWord = intent.getStringExtra("keyword")
        HttpUtils.httpHandlerGetBean(mHandler,HttpParser().getSearchURL(mKeyWord),Bean.SEARCHBEAN)
    }

    private fun initEvent(){
        // scroll for renew
        mSearchRecycle.addOnScrollListener(object : EndlessRecyclerOnScrollListener(){
            override fun onLoadMore() {
                mSearchAdapter.setLoadState(mSearchAdapter.LOADING)
                if(mSearchList.size < mSearchDataBuffer.size){
                    getData()
//                    mSearchAdapter.setLoadState(mSearchAdapter.LOADING_COMPLETE)
                }else{
                    mSearchAdapter.setLoadState(mSearchAdapter.LOADING_END)
                }
            }
        })

        mSearchAdapter.mOnClickListener = object :ItemOnClickListener{
            override fun OnItemClick(view: View?, position: Int) {
                Toast.makeText(this@SearchResult,"onclick called",Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SearchResult,DisplayNews::class.java)
                intent.putExtra("url",mSearchList[position].mSearchItem.url)
                startActivity(intent)
            }
        }
    }

    private fun getData(){
        if(mSearchList.size <= mSearchDataBuffer.size){
            if(mStartIndex + mRefreshNum -1 < mSearchDataBuffer.size){
                for(i in mStartIndex until mStartIndex+mRefreshNum-1) mSearchList.add(mSearchDataBuffer[i])
                mStartIndex += mRefreshNum
            }else{
                mSearchList = mSearchDataBuffer
                mStartIndex = mSearchDataBuffer.size
            }
        }
    }
}