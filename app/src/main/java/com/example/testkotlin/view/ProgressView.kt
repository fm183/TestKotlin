package com.example.testkotlin.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.testkotlin.R

class ProgressView@JvmOverloads constructor(context: Context,
                                            attrs: AttributeSet? = null,
                                            defStyleAttr: Int = 0): View(context,attrs,defStyleAttr){
    private var typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressView)
    private var mProgress = typedArray.getFloat(R.styleable.ProgressView_progress,0f)
    private var mBaseColor = typedArray.getColor(R.styleable.ProgressView_baseColor,Color.WHITE)
    private var mProgressColor = typedArray.getColor(R.styleable.ProgressView_progressColor,Color.GREEN)
    private val paint = Paint() //定义一个画笔
    private val paint2 = Paint() //定义一个画笔
    private val edgeDistance = 10f
    private var widthX = 0
    private var heightY = 0
    private var rectF = RectF(edgeDistance, edgeDistance, widthX - edgeDistance, heightY - edgeDistance)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        widthX = MeasureSpec.getSize(widthMeasureSpec)
        heightY = MeasureSpec.getSize(heightMeasureSpec)

        if (widthX > heightY) {
            widthX = heightY
        }

        if (heightY > widthX) {
            heightY = widthX
        }

        setMeasuredDimension(widthX, heightY)
        rectF.right = widthX - edgeDistance
        rectF.bottom = heightY - edgeDistance
    }

    fun setProgress(progress: Int){
        val value = progress / 100f
        val valueAnimator = ValueAnimator.ofFloat(0f,value)
        valueAnimator.duration = 1000
        valueAnimator.addUpdateListener { animation ->
            mProgress = animation?.animatedValue as Float
            invalidate()
        }
        valueAnimator.start()
    }

    fun setBaseColor(color:Int){
        this.mBaseColor = color
    }

    fun setProgressColor(color: Int){
        this.mProgressColor = color
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        paint.color = mBaseColor
        paint.strokeWidth = edgeDistance

        paint2.color = mProgressColor
        paint2.style = Paint.Style.STROKE
        paint2.strokeCap = Paint.Cap.ROUND
        paint2.strokeWidth = edgeDistance

        val centerX = widthX / 2
        val centerY = heightY / 2
        val radius = (widthX / 2) - edgeDistance

        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(),radius,paint)

        canvas.drawArc(rectF,-90f,360 * mProgress,false,paint2)
    }
}