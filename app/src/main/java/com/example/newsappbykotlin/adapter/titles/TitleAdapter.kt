package com.example.newsappbykotlin.adapter.titles

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappbykotlin.R
import com.example.newsappbykotlin.utils.ItemOnClickListener

class TitleAdapter:RecyclerView.Adapter<TitleAdapter.MyViewHolder>{
    private var TAG:String = "Title Adapter Log: "
    var mDataList = mutableListOf<String>()
    private var mCurrentItem:Int = 0
    var mOnItemClickedListener:ItemOnClickListener? = null

    fun setCurrentPosition(position: Int){
        this.mCurrentItem = position
        notifyDataSetChanged()
    }

    constructor(mDataList: MutableList<String>) : super() {
        this.mDataList = mDataList
    }


    inner class MyViewHolder:RecyclerView.ViewHolder,View.OnClickListener{
        var mtext:TextView

        constructor(itemView: View) : super(itemView) {
            this.mtext = itemView.findViewById(R.id.tri_titles_tv)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.e(TAG,"title has been clicked!")
            mOnItemClickedListener?.OnItemClick(v, itemView.tag as Int)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view:View = LayoutInflater.from(parent.context).inflate(R.layout.title_recycle_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mtext.text = mDataList[position]
        if(position==mCurrentItem){
            holder.mtext.setTextColor(Color.BLACK)
        }else{
            holder.mtext.setTextColor(Color.GRAY)
        }
        holder.itemView.tag = position
    }


}