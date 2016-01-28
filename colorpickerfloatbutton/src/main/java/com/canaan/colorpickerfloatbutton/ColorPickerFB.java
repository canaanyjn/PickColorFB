package com.canaan.colorpickerfloatbutton;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 16/1/26.
 */
public class ColorPickerFB extends View {
    private static final String TAG = ColorPickerFB.class.getSimpleName();

    private int colorNum;
    private int colorSqureWidth;
    private int basicSqureColor;
    private List<String> colors = new ArrayList<>();
    private List<ColorSqure> colorSqures = new ArrayList<>();

    private int centerX,centerY;
    private int currentColorNum;
    private float gap;
    private int width,height;
    private int basicStartX = 20,basicStartY = 20;
    private int basicIncreaseWidth;
    private boolean isAnimationStart;
    private static int BASIC_ANIM_DURATION = 500;

    private Paint colorPaint;
    private ValueAnimator basicSqureAnimator;

    public ColorPickerFB(Context context) {
        this(context, null, 0);
    }

    public ColorPickerFB(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorPickerFB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        initAttr(context, attrs);
        initValues();
        initPaint();
        initBasicAnim();
    }

    private void initAttr(Context context,AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.ColorPickerFB,0,0);
            CharSequence[] entries = typedArray.getTextArray(R.styleable.ColorPickerFB_android_entries);
            if (entries != null) {
                for (int i = 0;i<entries.length;i++) {
                    colors.add(entries[i].toString());
                }

            }
            colorNum = typedArray.getInteger(R.styleable.ColorPickerFB_colorNum,1);
            colorSqureWidth = typedArray.getInteger(R.styleable.ColorPickerFB_colorSqureWidth,50);
        }
    }

    private void initPaint() {
        colorPaint = new Paint();
        colorPaint.setColor(basicSqureColor);
        colorPaint.setStyle(Paint.Style.FILL);
        colorPaint.setAntiAlias(true);
    }

    private void initValues() {
        basicSqureColor = Color.parseColor("#DE7216");
        gap = 90 / (colors.size() - 1) ;
        centerX = width/2;
        centerY = height/2;

        for (int i = 0;i<colors.size();i++) {
            ColorSqure colorSqure;
            if (i == colors.size()-1) {
                colorSqure = new ColorSqure(90,colors.get(i),colorSqureWidth,i);
            } else {
                colorSqure = new ColorSqure(gap*i,colors.get(i),colorSqureWidth,i);
            }
            addAnimatorUpdateListener(colorSqure);
            colorSqures.add(colorSqure);
        }
    }

    private void initBasicAnim() {
        basicSqureAnimator = ValueAnimator.ofFloat(0,1);
        basicSqureAnimator.setDuration(BASIC_ANIM_DURATION)
                .setInterpolator(new AccelerateDecelerateInterpolator());
        basicSqureAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = ((Float)animation.getAnimatedValue()).floatValue();
                basicIncreaseWidth = Math.round(colorSqureWidth * progress);
            }
        });
    }

    private void addAnimatorUpdateListener(final ColorSqure colorSqure) {
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = ((Float)animation.getAnimatedValue()).floatValue();
                colorSqure.setProgress(progress);
                invalidate();
            }
        };
        colorSqure.setUpdateListener(animatorUpdateListener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.setLayerType(LAYER_TYPE_SOFTWARE, null);

        colorPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));
        //draw basic squre
        canvas.drawRect(basicStartX, basicStartY, colorSqureWidth + basicIncreaseWidth + basicStartX,
                colorSqureWidth + basicIncreaseWidth +basicStartY, colorPaint);

        colorPaint.setMaskFilter(new BlurMaskFilter(3, BlurMaskFilter.Blur.SOLID));
        for (int i = 0;i < colorSqures.size();i++) {
            ColorSqure colorSqure = colorSqures.get(i);
            canvas.save();
            if (!isAnimationStart) {
                canvas.rotate(colorSqure.getAngle(), colorSqure.getRect().centerX(), colorSqure.getRect().centerY());
            } else {
                canvas.rotate(colorSqure.getAngle(),basicStartX + colorSqureWidth/2,basicStartY + colorSqureWidth/2);
            }
            colorPaint.setColor(Color.parseColor(colorSqure.getColor()));
            canvas.drawRect(colorSqure.getRect(), colorPaint);
            canvas.restore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0;i < colorSqures.size();i++) {
                    colorSqures.get(i).startAnimator();
                }
                basicSqureAnimator.start();
                isAnimationStart = true;
        }
        return false;
    }
}
