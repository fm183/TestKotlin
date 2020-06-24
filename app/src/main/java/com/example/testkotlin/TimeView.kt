package com.example.testkotlin

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin


class TimeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {

    private var mWidth = 0
    private var mHeight = 0
    private var edgeDistance = 1f
    private var paint = Paint()
    private var path = Path()
    private var textPaint = TextPaint()
    private var rectF = RectF(edgeDistance,edgeDistance,edgeDistance,edgeDistance)
    private var lineHeight = 40f
    private var count = 24
    private var startCount = 39
    private var endCount = 43

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

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

        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        paint.color = Color.TRANSPARENT
        paint.strokeWidth = edgeDistance

        val centerX = mWidth / 2
        val centerY = mHeight / 2
        val radios = (mWidth / 2) - edgeDistance
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), radios, paint)

        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL_AND_STROKE

        val allWidth = Math.PI * radios * 2

        val ringValue = (360 - count * 0.5f) / count
        val tmpWidth = allWidth / 360
        val lineWidth = (tmpWidth * ringValue) / 1.1

        rectF.right = centerX.toFloat()
        rectF.left = (centerX + lineWidth).toFloat()
        rectF.top = lineHeight
        rectF.bottom = lineHeight
        var rotateValue = 0f
        for (i in 0 until count) {
            canvas.save()
            if (i in startCount..endCount) {
                paint.color = Color.RED
            } else {
                paint.color = Color.BLUE
            }
            canvas.rotate(rotateValue, centerX.toFloat(), centerY.toFloat())
            rotateValue += ringValue + 0.5f
            path.moveTo(centerX.toFloat(), 10f)
            path.lineTo((centerX + lineWidth).toFloat(), 10f)
            path.lineTo((centerX + lineWidth - 3).toFloat(),  lineHeight)
            path.quadTo((centerX + lineWidth / 2f).toFloat(),lineHeight - 10,centerX.toFloat() + 3f, lineHeight)
//            path.lineTo(centerX.toFloat() + 3f,0f+lineHeight)
            path.close()
            canvas.drawPath(path, paint)
            canvas.restore()
        }
        canvas.save()

        textPaint.color = Color.BLACK
        textPaint.textSize = 40f
        textPaint.textAlign = Paint.Align.CENTER


        val text = "13:51:00"
        val text2 = "星期日"
        val startCenterX = (mWidth  - textPaint.measureText(text)) / 2 + lineHeight
        val startCenterX2 = (mWidth  - textPaint.measureText(text2)) / 2 + lineHeight - 15
        canvas.drawText(text, startCenterX, centerY.toFloat(), textPaint)
        canvas.drawText(text2, startCenterX2, centerY + 50f, textPaint)

        val textRadios = mWidth / 2 - lineHeight //文字构成的圆的半径
        val s = arrayOf("12", "3", "6", "9")
        for (i in 0..3) {
            var diffX = 0
            var diffY = 0
            if(i == 0){
                diffX = 10
                diffY = lineHeight.toInt()
            }else if(i == 1){
                diffX = (-lineHeight).toInt()
                diffY = 0
            }else if(i == 2){
                diffX = 10
                diffY = (-lineHeight).toInt()
            }else if(i == 3){
                diffX = lineHeight.toInt() + 10
                diffY = 10
            }
            // Math.PI  就是 π 3.1415926    实际上他就是180°
            val startX = (mWidth / 2 + textRadios * sin(Math.PI / 2 * i) - textPaint.measureText(s[i]) / 2 + diffX).toFloat()
            val startY = (mWidth / 2 - textRadios * cos(Math.PI / 2 * i) + textPaint.measureText(s[i]) / 2 + diffY).toFloat()
            canvas.drawText(s[i], startX, startY, textPaint)
        }

        canvas.save()

    }

    fun setCount(count: Int){
        this.count = count
    }

    fun setLineHeight(lineHeight:Float){
        this.lineHeight = lineHeight
    }

}