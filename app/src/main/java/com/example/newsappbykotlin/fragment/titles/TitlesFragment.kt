package com.example.newsappbykotlin.fragment.titles

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.newsappbykotlin.R
import com.example.newsappbykotlin.adapter.titles.ContentAdapter
import com.example.newsappbykotlin.adapter.titles.TitleAdapter
import com.example.newsappbykotlin.bean.Bean
import com.example.newsappbykotlin.bean.channelbean.ChannelBean
import com.example.newsappbykotlin.utils.HttpParser
import com.example.newsappbykotlin.utils.HttpUtils
import com.example.newsappbykotlin.utils.ItemOnClickListener


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TitlesFragment : Fragment(),ViewPager.OnPageChangeListener {
    private var param1: String? = null
    private var param2: String? = null

    //当前页面
    private var mCurrentIndex:Int = 0
    private lateinit var mView:View
    //RecycleView相关
    private var mTitleDataList = mutableListOf<String>()
    private lateinit var mTitleAdapter:TitleAdapter
    private lateinit var mTitleRecyclerView: RecyclerView

    //ViewPager相关
    private lateinit var mViewPager: ViewPager
    private var mViewPagerList = mutableListOf<Fragment>()
    private lateinit var mPagerAdapter: ContentAdapter

    private var mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if(msg.what == Bean.CAHNNELBEAN){
                val bean:ChannelBean = msg.obj as ChannelBean
                for(i in bean.result) mTitleDataList.add(i)
                //init viewpager
                val n:Int = mTitleDataList.size
                for(i in 0 until n){
                    mViewPagerList.add(ContentFragment(mTitleDataList[i]))
                }
                mTitleAdapter.notifyDataSetChanged()
                mPagerAdapter.notifyDataSetChanged()
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_titles, container, false)
        mView = view
        initData()
        initView(mView)
        initEvent()
        return view
    }

    private fun initData(){
        HttpUtils.httpHandlerGetBean(mHandler,HttpParser().getChannelURL(),Bean.CAHNNELBEAN)
    }

    private fun initView(view: View){
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mTitleRecyclerView = view.findViewById(R.id.ft_titles_tv)
        mTitleRecyclerView.layoutManager = layoutManager
        mTitleAdapter = TitleAdapter(mDataList = mTitleDataList)
        mTitleRecyclerView.adapter = mTitleAdapter

        mViewPager = view.findViewById(R.id.ft_content_vp)
        mPagerAdapter = ContentAdapter(fragmentManager,mViewPagerList)
        mViewPager.adapter = mPagerAdapter
    }

    private fun initEvent(){
        mTitleAdapter.mOnItemClickedListener = object: ItemOnClickListener {
            override fun OnItemClick(view: View?, position: Int) {
                setCurrent(position)
            }
        }

        mViewPager.addOnPageChangeListener(this)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TitlesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TitlesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun setCurrentPage(position:Int){
        mViewPager.currentItem = position
    }

    fun setCurrentRecycleView(position: Int){
        mTitleAdapter.setCurrentPosition(position)
        mTitleRecyclerView.smoothScrollToPosition(position)
    }

    fun setCurrent(position: Int){
        setCurrentPage(position)
        setCurrentRecycleView(position)
        mCurrentIndex = position
    }


    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        setCurrent(position)
    }
}