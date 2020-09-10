package com.example.testkotlin.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class PathView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    private var paint = Paint()
    private var path = Path()
    private var mWidth = 0
    private var mHeight = 0
    private var rectF = RectF()


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        paint.color = Color.BLACK
        paint.strokeWidth = 3f

        mWidth = if (width > height) {
            height
        } else {
            width
        }
        mHeight = if (height > width) {
            width
        } else {
            height
        }

        val centerX = mWidth / 2
        val centerY = mHeight / 2
        val radios = (mWidth / 2) - 10f

//        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(),radios,paint )

        val lineHeight = 80
        val lineWidth = 40
//        path.moveTo((centerX).toFloat(), (lineHeight + 1).toFloat())
//        path.quadTo((centerX + lineWidth / 2).toFloat(), (lineHeight - 10).toFloat(),(centerX + lineWidth).toFloat(), (1 +lineHeight).toFloat())

//        path.moveTo(300f,300f)
//        path.cubicTo(300f,300f,400f,200f,600f,300f)
        rectF.left = centerX.toFloat()
        rectF.right = (centerX + lineWidth * 2).toFloat()
        rectF.top = centerY.toFloat()
        rectF.bottom = (centerY + lineHeight * 2).toFloat()
        path.arcTo(rectF,0f,180f)
        canvas.drawPath(path, paint)

    }


}