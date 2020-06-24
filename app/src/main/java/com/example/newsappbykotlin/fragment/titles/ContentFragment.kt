package com.example.newsappbykotlin.fragment.titles

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappbykotlin.DisplayNews
import com.example.newsappbykotlin.R
import com.example.newsappbykotlin.adapter.titles.ContentRecycleAdapter
import com.example.newsappbykotlin.bean.Bean
import com.example.newsappbykotlin.bean.newsbean.NewsBean
import com.example.newsappbykotlin.item.TitleItem
import com.example.newsappbykotlin.utils.EndlessRecyclerOnScrollListener
import com.example.newsappbykotlin.utils.HttpParser
import com.example.newsappbykotlin.utils.HttpUtils
import com.example.newsappbykotlin.utils.ItemOnClickListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContentFragment : Fragment {

    private val TAG:String = "ContentFragment Log: "
    private var param1: String? = null
    private var param2: String? = null

    //recycle view things
    private lateinit var mRecycleAdapter: ContentRecycleAdapter
    private var mDataList = mutableListOf<TitleItem>()
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mTitle:String
    private lateinit var mRecyclerView: RecyclerView
    private var mRequestNum:Int = 100
    private var mRefreshNum:Int = 10
    private var mDataBuffer = mutableListOf<TitleItem>()
    private var mStartIndex:Int = 0
    private var mHandler = @SuppressLint("HandlerLeak")
    object: Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if(msg.what == Bean.NEWSBEAN){
                val bean:NewsBean = msg.obj as NewsBean
                for(i in bean.result.list) mDataBuffer.add(TitleItem(i.title,i))
                getData()
                mRecycleAdapter.setLoadState(mRecycleAdapter.LOADING_COMPLETE)
            }
        }
    }

    constructor(mTitle: String) : super() {
        this.mTitle = mTitle
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_content, container, false)
        Log.e(TAG,"onCreateView Executed!")
        initView(view)
        initData()
        initEvent()
        return view
    }


    private fun initView(view: View){
        mLayoutManager = LinearLayoutManager(parentFragment?.context)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecycleAdapter = ContentRecycleAdapter(mDataList)
        mRecyclerView = view.findViewById(R.id.fc_content_rv)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mRecycleAdapter
    }

    private fun initData(){
        HttpUtils.httpHandlerGetBean(mHandler,HttpParser().getNewsURL(mTitle,mRequestNum),Bean.NEWSBEAN)
    }

    private fun initEvent(){
        // scroll for renew
        mRecyclerView.addOnScrollListener(object : EndlessRecyclerOnScrollListener(){
            override fun onLoadMore() {
                mRecycleAdapter.setLoadState(mRecycleAdapter.LOADING)
                if(mDataList.size<mDataBuffer.size){
                    getData()
                }else{
                    mRecycleAdapter.setLoadState(mRecycleAdapter.LOADING_END)
                }
            }
        })

        //init home page item click events
        mRecycleAdapter.mOnClickListener = object : ItemOnClickListener {
            override fun OnItemClick(view: View?, position: Int) {
                val intent: Intent = Intent(context, DisplayNews::class.java)
                intent.putExtra("url",mDataList[position].mDataNews.url)
                startActivity(intent)
            }
        }
    }

    private fun getData(){
        if(mDataList.size <= mDataBuffer.size){
            if(mStartIndex + mRefreshNum -1 < mDataBuffer.size){
                for(i in mStartIndex until mStartIndex+mRefreshNum-1) mDataList.add(mDataBuffer[i])
                mStartIndex += mRefreshNum
            }else{
                mDataList = mDataBuffer
                mStartIndex = mDataBuffer.size
            }
        }
    }
}