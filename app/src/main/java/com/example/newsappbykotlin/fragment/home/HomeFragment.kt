package com.example.newsappbykotlin.fragment.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.Display
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappbykotlin.DisplayNews
import com.example.newsappbykotlin.R
import com.example.newsappbykotlin.adapter.home.HomeAdapter
import com.example.newsappbykotlin.bean.Bean
import com.example.newsappbykotlin.bean.newsbean.NewsBean
import com.example.newsappbykotlin.bean.newsbean.NewsList
import com.example.newsappbykotlin.item.HomeItem
import com.example.newsappbykotlin.utils.EndlessRecyclerOnScrollListener
import com.example.newsappbykotlin.utils.HttpParser
import com.example.newsappbykotlin.utils.HttpUtils
import com.example.newsappbykotlin.utils.ItemOnClickListener
import java.lang.Thread.sleep
import java.util.concurrent.CopyOnWriteArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePageFragment : Fragment() {
    private val TAG: String? = "HomeFragment Log: "
    private var param1: String? = null
    private var param2: String? = null
    //home recycleview
    private var mRequestNum:Int = 40
    private lateinit var mHomeRecycle: RecyclerView
    private var mHomeItemList:MutableList<HomeItem> = mutableListOf()
    private lateinit var mHomeManager: LinearLayoutManager
    private lateinit var mHomeAdapter: HomeAdapter
    private var mHomeItemBuffer: MutableList<HomeItem> = mutableListOf()
    private var mStartIndex:Int = 0
    private var mRefreshNum:Int = 10
    private var mHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == Bean.NEWSBEAN){
                val newsBean: NewsBean = msg.obj as NewsBean
                newsBean.result.list.forEach { item: NewsList ->
                    mHomeItemBuffer.add(HomeItem(item))
                }
                getData()
                mHomeAdapter.setLoadState(mHomeAdapter.LOADING_COMPLETE)
            }
        }
    }

    private fun initData(){
        HttpUtils.httpHandlerGetBean(mHandler,HttpParser().getNewsURL("头条",mRequestNum),Bean.NEWSBEAN)
    }

    private fun initView(view:View){
        //init home recycle_view
        mHomeRecycle = view.findViewById(R.id.fh_news_rv)
        mHomeManager = LinearLayoutManager(parentFragment?.context)
        mHomeManager.orientation = LinearLayoutManager.VERTICAL
        mHomeRecycle.layoutManager = mHomeManager
        mHomeAdapter = HomeAdapter(mHomeItemList)
        mHomeRecycle.adapter = mHomeAdapter
    }

    private fun initEvent(){
        // scroll for renew
        mHomeRecycle.addOnScrollListener(object :EndlessRecyclerOnScrollListener(){
            override fun onLoadMore() {
                mHomeAdapter.setLoadState(mHomeAdapter.LOADING)
                if(mHomeItemList.size<mHomeItemBuffer.size){
                    getData()
                }else{
                    mHomeAdapter.setLoadState(mHomeAdapter.LOADING_END)
                }
            }
        })

        //init home page item click events
        mHomeAdapter.mOnClickListener = object : ItemOnClickListener {
            override fun OnItemClick(view: View?, position: Int) {
                val intent: Intent = Intent(context,DisplayNews::class.java)
                intent.putExtra("url",mHomeItemList[position].mData.url)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_home, container, false)
        initData()
        initView(view)
        initEvent()
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomePageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomePageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getData(){
        if(mHomeItemList.size <= mHomeItemBuffer.size){
            if(mStartIndex + mRefreshNum -1 < mHomeItemBuffer.size){
                for(i in mStartIndex until mStartIndex+mRefreshNum-1) mHomeItemList.add(mHomeItemBuffer[i])
                mStartIndex += mRefreshNum
            }else{
                mHomeItemList = mHomeItemBuffer
                mStartIndex = mHomeItemBuffer.size
            }
        }
    }
}