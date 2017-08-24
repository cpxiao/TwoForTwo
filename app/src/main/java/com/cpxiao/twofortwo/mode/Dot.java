package com.cpxiao.twofortwo.mode;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.cpxiao.gamelib.mode.common.Sprite;

/**
 * @author cpxiao on 2017/08/22.
 */

public class Dot extends Sprite {

    private boolean isSelected = false;
    private long mNumber;
    private int mColor;

    public void reset(long number, int color) {
        setFrame(0);
        setNumber(number);
        setColor(color);
    }

    public void setNumber(long number) {
        mNumber = number;
    }

    public long getNumber() {
        return mNumber;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public int getColor() {
        return mColor;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private float getR() {
        RectF rectF = getDrawRectF();
        float maxR = 0.5F * Math.min(rectF.width(), rectF.height());
        long frame = getFrame();
        if (frame < 10) {
            return maxR * frame / 10;
        } else {
            return maxR;
        }
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        float r = getR();
        if (isSelected) {
            drawSelectedCircle(canvas, paint, r);
        }
        drawSmallCircle(canvas, paint, r);
        drawNumber(canvas, paint);
    }

    private void drawSelectedCircle(Canvas canvas, Paint paint, float r) {
        r = 1.2F * r;
        paint.setColor(mColor);
        paint.setAlpha(100);
        canvas.drawCircle(getCenterX(), getCenterY(), r, paint);
    }

    private void drawSmallCircle(Canvas canvas, Paint paint, float r) {
        paint.setColor(mColor);
        paint.setAlpha(255);
        canvas.drawCircle(getCenterX(), getCenterY(), r, paint);
    }

    private void drawNumber(Canvas canvas, Paint paint) {
        String msg;
        if (mNumber < 1024L) {
            msg = String.valueOf(mNumber);
        } else if (mNumber < 1024L * 1024) {
            msg = mNumber / 1024L + "K";
        } else if (mNumber < 1024L * 1024 * 1024) {
            msg = mNumber / (1024L * 1024) + "M";
        } else if (mNumber < 1024L * 1024 * 1024 * 1024) {
            msg = mNumber / (1024L * 1024 * 1024) + "G";
        } else {
            msg = mNumber / (1024L * 1024 * 1024 * 1024) + "T";
        }

        paint.setColor(Color.WHITE);
        paint.setTextSize(0.68F * getR());
        canvas.drawText(msg, getCenterX(), getCenterY() + 0.1F * getHeight(), paint);
    }


}