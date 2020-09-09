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
            CircleTimeView circleTimeView = weakReference.get();
            if (circleTimeView == null) {
                return;
            }
            postDelayed(circleTimeView.runnable,500);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateTime();
        }
    };


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
    private int[] colors = new int[]{Color.WHITE,Color.RED,Color.BLUE};
    private SparseIntArray showFirstProgressPositionArray = new SparseIntArray();
    private SparseIntArray showSecondProgressPositionArray = new SparseIntArray();
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
        int count = 0;
        for (int i = 0; i < COUNT;i ++){
            int CHILD_COUNT = 4;
            float tmpAngle = mAngle / CHILD_COUNT;
            float tmpStartAngle = startAngle;
            for (int j = 0; j < CHILD_COUNT; j ++){
                int value = showFirstProgressPositionArray.indexOfKey(count) < 0 ? 0 : 1;
                if (value == 0) {
                    value = showSecondProgressPositionArray.indexOfKey(count) < 0 ? 0 : 2;
                }
                paintArc.setColor(colors[value]);
                count ++;
                canvas.drawArc(rectF, tmpStartAngle, tmpAngle, false, paintArc);
                tmpStartAngle += tmpAngle;
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
        showFirstProgressPositionArray.clear();
        for (int i : positions){
            showFirstProgressPositionArray.put(i,i);
        }
        postInvalidate();
    }

    public void setDefaultColor(int color){
        colors[0] = color;
        postInvalidate();
    }

    public void setFirstColor(int color){
        colors[1] = color;
        postInvalidate();
    }


    public void setSecondColor(int color){
        colors[2] = color;
        postInvalidate();
    }

    public void setFirstProgressPosition(int startPosition,int endPosition){
        showFirstProgressPositionArray.clear();
        for (int i = startPosition - 1;i < endPosition;i ++){
            showFirstProgressPositionArray.put(i,1);
        }
        postInvalidate();
    }

    public void setSecondProgressPosition(int startPosition,int endPosition){
        showSecondProgressPositionArray.clear();
        for (int i = startPosition - 1;i < endPosition;i ++){
            showSecondProgressPositionArray.put(i,2);
        }
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

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (myWeakHandler != null) {
            myWeakHandler.removeCallbacks(runnable);
        }
    }
}
