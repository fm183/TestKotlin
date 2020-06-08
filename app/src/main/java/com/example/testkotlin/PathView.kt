package com.example.testkotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class PathView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    private var paint = Paint()
    private var path = Path()


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        paint.color = Color.BLACK

        path.moveTo(width / 2f - 100,height / 2f - 100)
        path.quadTo(width / 2f - 50,height / 2f - 150,width / 2f,height / 2f)
        canvas.drawPath(path,paint)

    }


}