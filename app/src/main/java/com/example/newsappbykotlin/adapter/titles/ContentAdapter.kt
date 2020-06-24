package com.example.newsappbykotlin.adapter.titles

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ContentAdapter: FragmentStatePagerAdapter{
    private var mViewList = mutableListOf<Fragment>()

    constructor(fm: FragmentManager?, mViewList: MutableList<Fragment>) : super(fm!!) {
        this.mViewList = mViewList
    }

    constructor(fm: FragmentManager, behavior: Int, mViewList: MutableList<Fragment>) : super(fm, behavior) {
        this.mViewList = mViewList
    }


    override fun getItem(position: Int): Fragment {
        return mViewList[position]
    }

    override fun getCount(): Int {
        return mViewList.size
    }


}