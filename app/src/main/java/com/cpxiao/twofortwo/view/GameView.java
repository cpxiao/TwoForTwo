package com.cpxiao.twofortwo.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.cpxiao.R;
import com.cpxiao.androidutils.library.utils.ThreadUtils;
import com.cpxiao.gamelib.views.BaseSurfaceViewFPS;
import com.cpxiao.twofortwo.mode.Dot;
import com.cpxiao.twofortwo.mode.extra.ColorExtra;
import com.cpxiao.twofortwo.mode.extra.GameMode;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

import static com.cpxiao.twofortwo.mode.extra.ColorExtra.getRandomColor;

/**
 * @author cpxiao on 2017/08/22.
 */

public class GameView extends BaseSurfaceViewFPS {

    private long mScore = 0;
    private int mCountX = GameMode.COUNT_XY_DEFAULT;
    private int mCountY = GameMode.COUNT_XY_DEFAULT;

    private Dot[][] mDotArray;

    private List<Dot> mDotList = new ArrayList<>();

    private Dot mLastDot = null;

    private boolean isGameOver = false;

    public GameView(Context context, int countX, int countY) {
        super(context);
        mCountX = countX;
        mCountY = countY;
    }

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void timingLogic() {

    }

    private void showGameOverDialog() {
        ThreadUtils.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Dialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle(R.string.game_over)
                        .setMessage(R.string.click_to_restart)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                initWidget();


                            }
                        })
                        .create();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });

    }

    /**
     * 判断是否game over
     *
     * @return true: game over; false: not game over
     */
    private boolean checkGameOver() {
        if (!isDotArrayLegal()) {
            return false;
        }
        for (int y = 0; y < mCountY; y++) {
            for (int x = 0; x < mCountX; x++) {
                Dot dot = mDotArray[y][x];
                //重新开始的时候，初始化可能还未赋值
                if (dot == null) {
                    return false;
                }
                //判断右边
                int xRight = x + 1;
                if (xRight < mCountX) {
                    Dot dotR = mDotArray[y][xRight];
                    if (dotR == null || dot.getNumber() == dotR.getNumber()) {
                        return false;
                    }
                }
                //判断下边
                int yBottom = y + 1;
                if (yBottom < mCountY) {
                    Dot dotB = mDotArray[yBottom][x];
                    if (dotB == null || dot.getNumber() == dotB.getNumber()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isDotArrayLegal() {
        return mDotArray != null && mDotArray.length == mCountY && mDotArray[0].length == mCountX;
    }

    @Override
    protected void initWidget() {
        isGameOver = false;
        mScore = 0;

        float paddingLR = 0.04F * mViewWidth;
        float paddingT = Resources.getSystem().getDisplayMetrics().density * 80;
        float dotWH = Math.min((mViewWidth - paddingLR * 2) / mCountX, (mViewHeight - 2 * paddingT) / mCountY);
        mDotArray = new Dot[mCountY][mCountX];
        for (int y = 0; y < mCountY; y++) {
            for (int x = 0; x < mCountX; x++) {
                Dot dot = new Dot();
                long number = ColorExtra.getRandomNumber();
                int color = ColorExtra.getRandomColor(getContext(), number);
                dot.reset(number, color);

                dot.setWidth(dotWH);
                dot.setHeight(dotWH);
                dot.setDrawRectFPercent(0.6F);
                dot.setDrawRectFPercent((0.13F * mViewWidth) / dotWH);
                dot.setX(paddingLR + x * dotWH);
                dot.setY(paddingT + y * dotWH);
                mDotArray[y][x] = dot;
            }
        }
    }

    @DebugLog
    @Override
    public void drawCache() {

        drawLinkLine(mCanvasCache, mPaint);

        drawDotArray(mCanvasCache, mPaint);

        drawScore(mCanvasCache, mPaint);

    }

    private void drawLinkLine(Canvas canvas, Paint paint) {
        if (mDotList.size() < 2) {
            return;
        }
        paint.setStrokeWidth(0.02F * mViewWidth);
        int color = mDotList.get(0).getColor();
        paint.setColor(color);

        for (int i = 0; i < mDotList.size() - 1; i++) {
            Dot dot0 = mDotList.get(i);
            Dot dot1 = mDotList.get(i + 1);
            canvas.drawLine(dot0.getCenterX(), dot0.getCenterY(), dot1.getCenterX(), dot1.getCenterY(), paint);
        }
    }

    private void drawDotArray(Canvas canvas, Paint paint) {
        if (!isDotArrayLegal()) {
            return;
        }
        for (int y = 0; y < mCountY; y++) {
            for (int x = 0; x < mCountX; x++) {
                Dot dot = mDotArray[y][x];
                dot.draw(canvas, paint);
            }
        }

    }


    private void drawScore(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLUE);
        paint.setTextSize(0.05F * mViewHeight);
        String scoreMsg = "" + mScore;
        canvas.drawText(scoreMsg, 0.5F * mViewWidth, 0.08F * mViewHeight, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float eventX = event.getX();
        float eventY = event.getY();

        if (action == MotionEvent.ACTION_UP) {
            merge();
            mLastDot = null;
            for (Dot dot : mDotList) {
                dot.setSelected(false);
            }
            mDotList.clear();

            if (!isGameOver && checkGameOver()) {
                isGameOver = true;
                showGameOverDialog();
            }
            return true;
        }


        for (int y = 0; y < mCountY; y++) {
            for (int x = 0; x < mCountX; x++) {
                Dot dot = mDotArray[y][x];
                if (dot.isClicked(eventX, eventY)) {
                    dot.setSelected(true);
                    if (dot != mLastDot) {
                        mDotList.add(dot);
                        mLastDot = dot;
                    }
                    if (DEBUG) {
                        Log.d(TAG, "onTouchEvent: mDotList.size() = " + mDotList.size());
                    }
                    return true;
                }
            }
        }


        return super.onTouchEvent(event);
    }

    private void merge() {
        if (!isCanBeMerged()) {
            return;
        }
        //处理最后一个Dot
        int size = mDotList.size();
        Dot lastDot = mDotList.get(size - 1);
        long score = lastDot.getNumber() * size;
        // 添加得分
        mScore += score;
        long mergeNumber = ColorExtra.manageNumber(score);
        lastDot.setNumber(mergeNumber);

        int mergeColor = getRandomColor(getContext(), mergeNumber);
        lastDot.setColor(mergeColor);

        //处理非最后一个Dot
        for (int i = 0; i < size - 1; i++) {
            Dot dot = mDotList.get(i);
            long number = ColorExtra.getRandomNumber();
            int color = getRandomColor(getContext(), number);
            dot.reset(number, color);
        }
    }

    /**
     * 检查是否可以合并。
     * 小于2或者有重复添加，就不能合并
     *
     * @return boolean
     */
    private boolean isCanBeMerged() {
        if (mDotList.size() < 2) {
            return false;
        }
        for (int i = 0; i < mDotList.size(); i++) {
            Dot dot = mDotList.get(i);
            for (int j = i; j < mDotList.size(); j++) {
                if (j > i && dot == mDotList.get(j)) {
                    //重复添加
                    return false;
                }
            }
        }

        //判断数值是否一样
        long value = mDotList.get(0).getNumber();
        for (Dot dot : mDotList) {
            if (value != dot.getNumber()) {
                return false;
            }
        }
        return true;
    }
}