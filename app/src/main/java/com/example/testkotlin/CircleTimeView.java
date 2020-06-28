package com.example.testkotlin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

public class CircleTimeView extends View {

    private static final class MyWeakHandler extends Handler{

        private WeakReference<CircleTimeView> weakReference;

        private MyWeakHandler(CircleTimeView circleTimeView){
            weakReference = new WeakReference<>(circleTimeView);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            final CircleTimeView circleTimeView = weakReference == null ? null : weakReference.get();
            if (circleTimeView == null || circleTimeView.myWeakHandler == null) {
                return;
            }
            circleTimeView.myWeakHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    circleTimeView.updateTime();
                }
            },500);
        }
    }


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
    private int[] colors = new int[]{0xFFDC143C,0xFFFFD700,0xFF0000FF,0xFF00FF7F};
    private SparseIntArray showProgressPositionArray = new SparseIntArray();
    private Rect rect;
    private final String[] texts = new String[]{"12","03","06","09"};
    private MyWeakHandler myWeakHandler;

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

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(30);

        mAngle = (360f - COUNT * MARGIN_ANGLE) / COUNT;
        rectF = new RectF();
        rect = new Rect();
        myWeakHandler = new MyWeakHandler(this);
        updateTime();
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
            if (showProgressPositionArray.indexOfKey(i) >= 0) {
                int CHILD_COUNT = 4;
                float tmpAngle = mAngle / CHILD_COUNT;
                float tmpStartAngle = startAngle;
                for (int j = 0; j < CHILD_COUNT; j ++){
                    paintArc.setColor(colors[j]);
                    canvas.drawArc(rectF, tmpStartAngle, tmpAngle, false, paintArc);
                    tmpStartAngle += tmpAngle;
                }
            }else {
                paintArc.setColor(arcColor);
                canvas.drawArc(rectF, startAngle, mAngle, false, paintArc);
            }

            startAngle += mAngle + MARGIN_ANGLE;
        }


        textPaint.getTextBounds(texts[0],0,texts[0].length(),rect);
        canvas.drawText(texts[0],mCenterX,(mCenterY - mRadio) * 2 + (rect.bottom - rect.top) + 20,textPaint);
        canvas.drawText(texts[1],mCenterX + mRadio - (rect.right - rect.left) - circle_width / 2,mCenterY,textPaint);
        canvas.drawText(texts[2],mCenterX ,mCenterY + mRadio - (rect.bottom - rect.top) - circle_width / 2,textPaint);
        canvas.drawText(texts[3],(mCenterX - mRadio) * 2 + (rect.right - rect.left),mCenterY,textPaint);

        String[] times = getTimes();
        String text = times[0];
        textPaint.setTextSize(60);
        textPaint.getTextBounds(text,0,text.length(),rect);
        canvas.drawText(text,mCenterX,mCenterY - (rect.bottom - rect.top) / 2f,textPaint);

        String text2 = times[1];
        textPaint.setTextSize(30);
        textPaint.getTextBounds(text2,0,text2.length(),rect);
        canvas.drawText(text2,mCenterX,mCenterY + (rect.bottom - rect.top) / 2f,textPaint);
    }

    public void setProgressPosition(int ... positions){
        showProgressPositionArray.clear();
        for (int i : positions){
            showProgressPositionArray.put(i,i);
        }
        postInvalidate();
    }

    public void setProgressPositionColors(int ... colors){
        if (colors.length != 4) {
            return;
        }
        this.colors = colors;
        postInvalidate();
    }

    private String[] getTimes(){
        String[] strings = new String[2];
        strings[0] = DateTimeUtils.formatTime(System.currentTimeMillis());
        strings[1] = DateTimeUtils.getWeek(System.currentTimeMillis());
        return strings;
    }

    public void updateTime(){
        postInvalidate();
        myWeakHandler.sendEmptyMessage(0);
    }


}
