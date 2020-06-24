package com.example.testkotlin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class CircleTimeView extends View {

    private Paint paintCircle;
    private Paint paintArc;
    private static final int circleColor = Color.TRANSPARENT;
    private static final int arcColor = Color.WHITE;
    private float circle_width = 20;
    private int mWidth;
    private int mHeight;
    private float mRadio;
    private float mCenterX;
    private float mCenterY;
    private final int COUNT = 24;
    private final int MARGIN_ANGLE = 1;
    private float mAngle;
    private RectF rectF;
    private TextPaint textPaint;

    public CircleTimeView(Context context) {
        this(context,null);
    }

    public CircleTimeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleTimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        circle_width = getResources().getDimension(R.dimen.common_dp20);
        paintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCircle.setColor(circleColor);
        paintCircle.setAntiAlias(true);
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setStrokeWidth(circle_width);

        paintArc = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintArc.setColor(arcColor);
        paintArc.setAntiAlias(true);
        paintArc.setStyle(Paint.Style.STROKE);
        paintArc.setStrokeWidth(circle_width);

        mAngle = (360f - COUNT * MARGIN_ANGLE) / COUNT;
        rectF = new RectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(CircleTimeView.class.getSimpleName(),"onSizeChanged w="+w+",h="+h+",oldW="+oldw+",oldH="+oldh);
        if (w == 0 || h == 0) {
            return;
        }

        mWidth = Math.min(w, h);
        mHeight = Math.min(w, h);
        mRadio = (mWidth - circle_width) / 2f;
        mCenterX = mWidth / 2f;
        mCenterY = mHeight / 2f;
        Log.d(CircleTimeView.class.getSimpleName(),"onSizeChanged mWidth="+mWidth+",mHeight="+mHeight+",mRadio="+mRadio+",mCenterX="+mCenterX
        +",mCenterY="+mCenterY);

        rectF.left = mCenterX - mRadio;
        rectF.top = mCenterY - mRadio;
        rectF.right = mCenterX + mRadio;
        rectF.bottom = mCenterY + mRadio;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mWidth == 0 || mHeight == 0) {
            return;
        }
        canvas.drawCircle(mCenterX,mCenterY,mRadio,paintCircle);

        float startAngle = -90;
        for (int i = 0; i < COUNT;i ++){
            canvas.drawArc(rectF, startAngle, mAngle, false, paintArc);
            startAngle += mAngle + MARGIN_ANGLE;
        }



    }
}
