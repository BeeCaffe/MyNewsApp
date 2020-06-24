package com.example.newsappbykotlin.adapter.titles

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappbykotlin.R
import com.example.newsappbykotlin.item.TitleItem
import com.example.newsappbykotlin.utils.ItemOnClickListener
import com.example.newsappbykotlin.utils.MyUtils

class ContentRecycleAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private val TAG:String="HomeAdapter Log: "
    private var  mDataList:List<TitleItem>
    private lateinit var mView: View
    var mOnClickListener: ItemOnClickListener? = null

    //view type and status
    var TYPE_ITEM: Int = 1
    var TYPE_FOTTER: Int = 2
    var LOADING:Int = 1
    var LOADING_COMPLETE = 2
    var LOADING_END = 3
    private var loadState:Int = 2



    constructor(mDataList: List<TitleItem>) : super() {
        this.mDataList = mDataList
    }


    override fun getItemViewType(position: Int): Int {
        if(position+1 == itemCount){
            return TYPE_FOTTER
        }else{
            return TYPE_ITEM
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var mtitle: TextView
        var mimage: ImageView
        var mcontent: TextView

        init {
            this.mtitle = itemView.findViewById(R.id.hri_title_tv)
            this.mimage = itemView.findViewById(R.id.hri_image_iv)
            this.mcontent = itemView.findViewById(R.id.hri_content_tv)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.e(TAG,"onClicked called!")
            mOnClickListener?.OnItemClick(v, itemView.tag as Int)
        }
    }

    class FooterHolder:RecyclerView.ViewHolder{
        var mLoadingBar: ProgressBar
        var mLoadingTxt: TextView
        var mEndLL: TextView

        constructor(itemView: View) : super(itemView) {
            mLoadingBar = itemView.findViewById(R.id.rb_loading_pb)
            mLoadingTxt = itemView.findViewById(R.id.rb_loading_tv)
            mEndLL = itemView.findViewById(R.id.rb_end_tv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == TYPE_ITEM) {
            mView = LayoutInflater.from(parent.context)
                .inflate(R.layout.news_recycle_item, parent, false)
            return MyViewHolder(mView)
        }else if(viewType == TYPE_FOTTER){
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.referse_bar,parent,false)
            return FooterHolder(view)
        }
        return MyViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return mDataList.size+1
    }


    override fun onBindViewHolder(holderMy: RecyclerView.ViewHolder, position: Int) {
        if(holderMy is MyViewHolder) {
            val view:MyViewHolder = holderMy
            val data: TitleItem = mDataList[position]
            view.mtitle.setText(data.mDataNews.title)

            var content:String = data.mDataNews.content
            content = MyUtils.eraseBracketsInString(content)
            view.mcontent.setText(content)
            val imgUrl:String = data.mDataNews.pic
            val img: Bitmap? = MyUtils.httpGetImage(imgUrl)
            if (img != null) {
                view.mimage.setImageBitmap(img)
            }
        }else if(holderMy is FooterHolder){
            val footViewHolder:FooterHolder = holderMy
            when(loadState){
                LOADING -> {
                    footViewHolder.mLoadingBar.visibility = View.VISIBLE
                    footViewHolder.mLoadingTxt.visibility = View.VISIBLE
                    footViewHolder.mEndLL.visibility = View.GONE
                }
                LOADING_COMPLETE -> {
                    footViewHolder.mLoadingBar.visibility = View.VISIBLE
                    footViewHolder.mLoadingTxt.visibility = View.VISIBLE
                    footViewHolder.mEndLL.visibility = View.GONE
                }
                LOADING_END -> {
                    footViewHolder.mLoadingBar.visibility = View.GONE
                    footViewHolder.mLoadingTxt.visibility = View.GONE
                    footViewHolder.mEndLL.visibility = View.VISIBLE
                }
            }
        }
        holderMy.itemView.tag = position
    }

    fun setLoadState(loadState:Int){
        this.loadState = loadState
        notifyDataSetChanged()
    }
}