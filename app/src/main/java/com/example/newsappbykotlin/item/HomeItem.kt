package com.example.newsappbykotlin.item

import com.example.newsappbykotlin.bean.newsbean.NewsList

class HomeItem {
    var mData: NewsList
    get() = field
    set(value) {
        field =value}

    constructor(mData: NewsList) {
        this.mData = mData
    }
}