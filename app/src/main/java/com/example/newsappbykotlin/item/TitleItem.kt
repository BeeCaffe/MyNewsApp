package com.example.newsappbykotlin.item

import com.example.newsappbykotlin.bean.channelbean.ChannelBean
import com.example.newsappbykotlin.bean.newsbean.NewsList

class TitleItem {
    var mData:String

    lateinit var mDataNews:NewsList

    constructor(mData: String) {
        this.mData = mData
    }

    constructor(mData: String, mDataNews: NewsList) {
        this.mData = mData
        this.mDataNews = mDataNews
    }
}