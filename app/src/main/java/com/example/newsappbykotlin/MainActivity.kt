package com.example.newsappbykotlin

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappbykotlin.adapter.home.HomeAdapter
import com.example.newsappbykotlin.bean.Bean
import com.example.newsappbykotlin.bean.newsbean.NewsBean
import com.example.newsappbykotlin.bean.newsbean.NewsList
import com.example.newsappbykotlin.dyi.IconWithTextview
import com.example.newsappbykotlin.fragment.home.HomePageFragment
import com.example.newsappbykotlin.fragment.search.SearchFragment
import com.example.newsappbykotlin.fragment.titles.TitlesFragment
import com.example.newsappbykotlin.item.HomeItem
import com.example.newsappbykotlin.utils.HttpParser
import com.example.newsappbykotlin.utils.HttpUtils

class MainActivity : FragmentActivity(),View.OnClickListener {
    private var mRequestNums:Int = 10
    private lateinit var mIconHome:IconWithTextview
    private lateinit var mIconSearch:IconWithTextview
    private lateinit var mIconTitles:IconWithTextview

    //three basic fragment
    private lateinit var mHomeFrag: HomePageFragment
    private lateinit var mSearchFrag: SearchFragment
    private lateinit var mTitleFrag: TitlesFragment
    private lateinit var mCurFrag:Fragment

    //fragment manager
    private lateinit var mFragManager: FragmentManager
    private lateinit var mFragTrans: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initEvent()
    }

    fun initView(){
        //init three basic choose buttom
        mIconHome = findViewById(R.id.am_home_iwt)
        mIconSearch = findViewById(R.id.am_search_iwt)
        mIconTitles = findViewById(R.id.am_titles_iwt)
        mIconHome.setViewAlpha(1.0f)
        //init three basic fragment
        mHomeFrag = HomePageFragment()
        mSearchFrag =
            SearchFragment()
        mTitleFrag = TitlesFragment()
        //init fragment
        mFragManager = supportFragmentManager
        mFragTrans = mFragManager.beginTransaction()
        mFragTrans.replace(R.id.am_fragment_ll,mHomeFrag)
        mFragTrans.commit()
        mCurFrag = mHomeFrag

    }

    fun initEvent(){
        mIconHome.setOnClickListener(this)
        mIconSearch.setOnClickListener(this)
        mIconTitles.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        resetView()
        when(v?.id){
            R.id.am_home_iwt -> {
                mIconHome.setViewAlpha(1.0f)
                switchToFrag(R.id.am_fragment_ll,mHomeFrag)
            }
            R.id.am_titles_iwt -> {
                mIconTitles.setViewAlpha(1.0f)
                switchToFrag(R.id.am_fragment_ll,mTitleFrag)
            }
            R.id.am_search_iwt -> {
                mIconSearch.setViewAlpha(1.0f)
                switchToFrag(R.id.am_fragment_ll,mSearchFrag)
            }
        }
    }

    private fun resetView(){
        mIconHome.setViewAlpha(0.0f)
        mIconTitles.setViewAlpha(0.0f)
        mIconSearch.setViewAlpha(0.0f)
    }

    private fun switchToFrag(baseViewId:Int, to:Fragment){
        if (mCurFrag !== to) {
            val transaction: FragmentTransaction =
                mFragManager.beginTransaction()
            if (!to.isAdded) { // 判断是否被add过
                // 隐藏当前的fragment，将 下一个fragment 添加进去
                transaction.hide(mCurFrag).add(baseViewId, to).commit()
            } else {
                // 隐藏当前的fragment，显示下一个fragment
                transaction.hide(mCurFrag).show(to).commit()
            }
            mCurFrag = to
        }
    }
}