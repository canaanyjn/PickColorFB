package com.canaan.colorpickerfloatbutton;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by mac on 16/1/27.
 */
public class ColorSqure {
    private float angle;
    private String color;
    private int width;
    private int index;

    private Rect rect = new Rect();
    private ValueAnimator animator;
    private ValueAnimator.AnimatorUpdateListener updateListener;

    private int startX = 20,startY = 20;
    private static final int START_DELAY = 70;
    private static final int ANIM_DURATION = 500;

    public ColorSqure(float angle, String color, int width,int index) {
        this.angle = angle;
        this.color = color;
        this.width = width;
        this.index = index;

        rect.set(startX,startY,startX+width,startY+width);
        initAnimator();
    }

    private void initAnimator() {
        animator = ValueAnimator.ofFloat(0,1f);
        animator.setDuration(ANIM_DURATION);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    public void startAnimator() {
        animator.setStartDelay(START_DELAY * index);
        animator.start();
    }

    public float getAngle() {
        return angle;
    }

    public String getColor() {
        return color;
    }

    public Rect getRect() {
        return rect;
    }

    public int getWidth() {
        return width;
    }


    public void setUpdateListener(ValueAnimator.AnimatorUpdateListener updateListener) {
        this.updateListener = updateListener;
        animator.addUpdateListener(updateListener);
    }

    public void setProgress(float progress) {
        int temperX = (int)(2 * width * progress);
        this.rect.set(startX+temperX,startY,startX+width+temperX,startY+width);
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }
}
