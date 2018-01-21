package com.example.a65175.myapplication;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import java.util.logging.LogRecord;

/**
 * Created by 65175 on 2018/1/21.
 */

public class BarrageView  extends FrameLayout {
    private static ArrayList<Barrage> data = new ArrayList<>();
    private int nowIndex = 0; //data 当前坐标
    private Bitmap nowBitmap ;
    int width ; //
    int height ;
    float scale;
    FrameLayout mFramelayout;
    FrameLayout.LayoutParams mLayoutParams;
    static boolean IS_START = false;
    long alltime;
    long starttime;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Barrage barrage = (Barrage) msg.getData().getSerializable("barrage");
            final LinearLayout layout = (LinearLayout) LayoutInflater.
                    from(getContext()).inflate(R.layout.barrageview_item,null);
            layout.setLayoutParams(mLayoutParams);
            //
            layout.setY(getRamdomY());
            layout.setX(width + layout.getWidth());
            TextView textView = (TextView) layout.findViewById(R.id.barrageview_item_tv);
            textView.setText(barrage.getBarrageInfo());
            mFramelayout.addView(layout);
            final ObjectAnimator anim = ObjectAnimator.ofFloat(layout, "translationX", -width);
            anim.setDuration(10000);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    anim.cancel();
                    layout.clearAnimation();
                    mFramelayout.removeView(layout);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            anim.start();
        }
    };
    int lastY;//上一次出现的Y值
    /**
     * 获得随机的Y轴的值
     *
     * @return
     */
    private float getRamdomY() {
        int tempY;
        int rY;
        int result = 0;
        // height * 2 / 4 - 25
        //首先随机选择一条道路
        int nowY = (int) (Math.random() * 3);
        switch (nowY) {
            case 0:
                nowY = avoidTheSameY(nowY,lastY);
                //第一条
                tempY = height / 4 - 25;
                rY = (int) (Math.random() * height / 4);
                if (rY >= height / 8) {
                    result = tempY + rY;
                } else {
                    result = tempY - rY + 50 ;
                }
                lastY = nowY;
                break;
            case 1:
                nowY = avoidTheSameY(nowY,lastY);
                //第二条
                tempY = height / 2 - 25;
                rY = (int) (Math.random() * height / 4);
                if (rY >= height / 8) {
                    result = tempY + rY;
                } else {
                    result = tempY - rY;
                }
                lastY = nowY;
                break;
            case 2:
                nowY = avoidTheSameY(nowY,lastY);
                //第三条
                tempY = height * 3 / 4 - 25;
                rY = (int) (Math.random() * height / 4);
                if (rY >= height / 8) {
                    result = tempY + rY -50;
                } else {
                    result = tempY - rY;
                }
                lastY = nowY;
                break;
        }
        return result;
    }

    /**
     * 避免Y重合的方法
     * @param lastY
     * @return
     */
    private int avoidTheSameY(int nowY,int lastY) {
        if (nowY == lastY) {
            nowY ++;
        }
        if (nowY == 4) {
            nowY = 0;
        }
        return nowY;
    }
    public BarrageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth(); //宽度
        height = getHeight();   //高度
        init();
    }

    private void init() {
        setTime(600000);    //设置初始时长，改完记得删

        starttime = System.currentTimeMillis();

        scale = this.getResources().getDisplayMetrics().density;
        //获得自身实例
        mFramelayout = (FrameLayout) findViewById(R.id.barrageview);
        mLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, 50);

        if (IS_START) {
            //开始动画线程
            startBarrageView();
            IS_START = false;
        }
    }

    public void startBarrageView() {
        //开启线程发送弹幕
        new Thread() {
            @Override
            public void run() {

                while ((System.currentTimeMillis() - starttime < alltime)
                        && (nowIndex <= data.size() - 1)
                        ){

                    try {
                        //nowBitmap = getBitmapFromUrl(date.get(nowIndex).getBarrageUrl());
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("barrage",data.get(nowIndex));
                        nowIndex ++;
                        message.setData(bundle);
                        mHandler.sendMessage(message);
                        Thread.sleep((long) (Math.random() * 3000) + 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
        }.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    //设置数据
    public void setSentenceList(ArrayList<Barrage> date1) {
        data = date1;
        IS_START = true;
    }

    //获得视频总时长
    public void setTime(long time) {
        alltime = time;
    }


}
