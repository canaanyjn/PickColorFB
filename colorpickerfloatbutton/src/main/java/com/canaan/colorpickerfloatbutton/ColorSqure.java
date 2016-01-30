package com.canaan.colorpickerfloatbutton;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by mac on 16/1/27.
 */
public class ColorSqure {
    public interface openAnimStopListener {
        void openAnimStop(boolean isStop);
    }

    public interface closeAnimStopListener {
        void closeAnimStop(boolean isStop);
    }

    private float angle;
    private String color;
    private int width;
    private int index;

    private Rect rect = new Rect();
    private ValueAnimator openAnimator,closeAnimator;
    private ColorSqure.openAnimStopListener openAnimStopListener;
    private ColorSqure.closeAnimStopListener closeAnimStopListener;

    private int startX = 20,startY = 20;
    private static final int START_DELAY = 70;
    private static final int ANIM_DURATION = 500;

    public ColorSqure(float angle, String color, int width,int index) {
        this.angle = angle;
        this.color = color;
        this.width = width;
        this.index = index;

        rect.set(startX,startY,startX+width,startY+width);
        initOpenAnimator();
        initCloseAnimator();
    }

    private void initOpenAnimator() {
        openAnimator = ValueAnimator.ofFloat(0,1f);
        openAnimator.setDuration(ANIM_DURATION);
        openAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        openAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (openAnimStopListener != null && index == 4) {
                    openAnimStopListener.openAnimStop(true);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void startOpenAnimator() {
        openAnimator.setStartDelay(START_DELAY * index);
        openAnimator.start();
    }

    private void initCloseAnimator() {
        closeAnimator = ValueAnimator.ofFloat(1f,0);
        closeAnimator.setDuration(ANIM_DURATION);
        closeAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    public void startCloseAnimator() {
        closeAnimator.start();
    }

    public void reverseOpenAnimator() {
        openAnimator.reverse();
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

    public void setOpenAnimStopListener(ColorSqure.openAnimStopListener openAnimStopListener) {
        this.openAnimStopListener = openAnimStopListener;
    }

    public void setCloseAnimStopListener(ColorSqure.closeAnimStopListener closeAnimStopListener) {
        this.closeAnimStopListener = closeAnimStopListener;
    }

    public void setOpenUpdateListener(ValueAnimator.AnimatorUpdateListener updateListener) {
        openAnimator.addUpdateListener(updateListener);
    }

    public void setCloseUpdateListener(ValueAnimator.AnimatorUpdateListener updateListener) {
        closeAnimator.addUpdateListener(updateListener);
    }

    public void setOpenProgress(float progress) {
        int temperX = (int)(2 * width * progress);
        this.rect.set(startX + temperX, startY, startX + width + temperX, startY + width);
    }

    public void setCloseProgress(float progress) {
        int temperX = (int)(2*width*progress);
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
