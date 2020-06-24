package com.example.newsappbykotlin.dyi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import com.example.newsappbykotlin.MainActivity
import com.example.newsappbykotlin.R

class WebBar: LinearLayout, View.OnClickListener {
    private lateinit var mButtonBack:ImageButton
    private lateinit var mButtonNext:ImageButton
    private lateinit var mButtonHome:ImageButton
    private lateinit var mButtonShare:ImageButton
    private lateinit var mButtonMore:ImageButton

    constructor(ctx:Context,attrs:AttributeSet) : super(ctx,attrs) {
        LayoutInflater.from(ctx).inflate(R.layout.web_bar,this)
        initView()
        initEvent()
    }

    private fun initView(){
        mButtonBack = findViewById(R.id.wb_back_ib)
        mButtonHome = findViewById(R.id.wb_home_ib)
        mButtonNext = findViewById(R.id.wb_next_ib)
        mButtonShare = findViewById(R.id.wb_share_ib)
        mButtonMore = findViewById(R.id.wb_tools_ib)
    }

    private fun initEvent(){
        mButtonBack.setOnClickListener(this)
        mButtonHome.setOnClickListener(this)
        mButtonNext.setOnClickListener(this)
        mButtonShare.setOnClickListener(this)
        mButtonMore.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.wb_back_ib->{
                val ctx = context as Activity
                ctx.finish()
            }
            R.id.wb_home_ib->{
                val intent = Intent(context,MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                context.startActivity(intent)
            }
            R.id.wb_next_ib->{
                Toast.makeText(context,"Next has been clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.wb_share_ib->{
                Toast.makeText(context,"Share has been clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.wb_tools_ib->{
                Toast.makeText(context,"Tool has been clicked",Toast.LENGTH_SHORT).show()
            }
        }
    }
}