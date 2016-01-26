package com.canaan.colorpickerfloatbutton;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by mac on 16/1/26.
 */
public class ColorPickerFB extends View {

    private int colorNum;
    private int colorSqureWidth;
    private int basicSqureColor;
    private List<Integer> colors;

    private int centerX,centerY;
    private int currentColorNum;
    private float gap;
    private int width,height;

    private Paint colorPaint;

    public ColorPickerFB(Context context) {
        super(context,null,0);
    }

    public ColorPickerFB(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public ColorPickerFB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        initAttr(context,attrs);
        initPaint();
        initValues();
    }

    private void initAttr(Context context,AttributeSet attrs) {

    }

    private void initPaint() {
        colorPaint = new Paint();
        colorPaint.setColor(basicSqureColor);
        colorPaint.setStyle(Paint.Style.FILL);
        colorPaint.setAntiAlias(true);
    }

    private void initValues() {
        gap = 90 / colors.size();
        centerX = width/2;
        centerY = height/2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension(width,height);
        initValues();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //draw basic squre



    }
}
