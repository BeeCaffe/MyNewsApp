package com.example.newsappbykotlin.dyi

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import com.example.newsappbykotlin.R

class IconWithTextview:View {
    private lateinit var mIconBitmap: Bitmap
    private var mColor:Int = 0
    private var mText:String = ""
    private var mTextSize:Float = 0.0f

    //paint
    private var mCanvas:Canvas = Canvas()
    private lateinit var mBitmap: Bitmap
    private var mPaint: Paint = Paint()
    private var mAlpha:Int = 0
    private var mIconRect: Rect = Rect()
    private var mTextBound: Rect = Rect()
    private var mTextPaint: Paint = Paint()


    constructor(ctx:Context):super(ctx,null){
        init(ctx,null)
    }
    constructor(ctx:Context,attrs:AttributeSet):super(ctx,attrs){
        init(ctx,attrs)
    }
    constructor(ctx: Context, attrs: AttributeSet, def:Int):super(ctx,attrs,def){
        init(ctx,attrs)
    }

    @SuppressLint("CustomViewStyleable")
    private fun init(ctx:Context, attrs: AttributeSet?){
        val typeArray:TypedArray = ctx.obtainStyledAttributes(attrs, R.styleable.IconWithTextView)
        val bitmapDrawable:BitmapDrawable = typeArray.getDrawable(R.styleable.IconWithTextView_icon) as BitmapDrawable
        mIconBitmap = bitmapDrawable.bitmap
        mColor = typeArray.getColor(R.styleable.IconWithTextView_color,0)
        mText = typeArray.getText(R.styleable.IconWithTextView_text) as String
        mTextSize = typeArray.getDimension(R.styleable.IconWithTextView_text_size, 0F)
        typeArray.recycle()

        //init paint
        mTextPaint = Paint()
        mTextBound = Rect()
        mTextPaint.textSize = mTextSize
        mTextPaint.color = 0xFF333333.toInt()
        mTextPaint.getTextBounds(mText,0,mText.length,mTextBound)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val iconWidth:Int = Math.min(measuredWidth-paddingLeft-paddingRight,measuredHeight-paddingTop-mTextBound.height()-paddingBottom)
        val left:Int = measuredWidth/2 - iconWidth/2
        val top:Int = measuredHeight/2 - (iconWidth+mTextBound.height())/2
        mIconRect = Rect()
        mIconRect.set(left,top,left+iconWidth,top+iconWidth)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawBitmap(mIconBitmap,null,mIconRect,null)
        val alpha = Math.ceil((255*mAlpha).toDouble())
        setUpTargetBitmap(alpha = alpha)
        canvas?.drawBitmap(mBitmap,0.0f,0.0f,null)
        super.onDraw(canvas)
        drawSourceText(canvas,alpha)
        drawTargetText(canvas,alpha)
        super.onDraw(canvas)
    }

    fun setUpTargetBitmap(alpha: Double){
        mBitmap = Bitmap.createBitmap(measuredWidth,measuredHeight,Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
        mPaint = Paint()
        mPaint.color = mColor
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.alpha = alpha.toInt()
        mCanvas.drawRect(mIconRect,mPaint)
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        mPaint.alpha = 255
        mCanvas.drawBitmap(mIconBitmap,null,mIconRect,mPaint)
    }

    fun drawTargetText(canvas: Canvas?,alpha: Double){
        mTextPaint.color = mColor
        mTextPaint.alpha = alpha.toInt()
        val x: Int = measuredWidth/2 - mTextBound.width()/2
        val y: Int = mIconRect.bottom + mTextBound.height()
        canvas?.drawText(mText, x.toFloat(), y.toFloat(),mTextPaint)
    }

    fun drawSourceText(canvas: Canvas?,alpha: Double){
        mTextPaint.color = 0xFF333333.toInt()
        mTextPaint.alpha = (255 - alpha).toInt()
        val x:Int = measuredWidth/2 - mTextBound.width()/2
        val y:Int = mIconRect.bottom + mTextBound.height()
        canvas?.drawText(mText, x.toFloat(), y.toFloat(),mTextPaint)
    }

    fun setViewAlpha(param_aplha: Float){
        mAlpha = param_aplha.toInt()
        invalidateView()
    }

    fun invalidateView(){
        if(Looper.getMainLooper() == Looper.myLooper()){
            invalidate()
        }else{
            postInvalidate()
        }
    }
}