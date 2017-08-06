package com.mayinews.mv.home.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.IRefreshHeader;

/**
 * Created by gary on 2017/6/16 0016.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class MyProgress extends ProgressBar implements IRefreshHeader {
    private Paint paint;
    public MyProgress(Context context) {
        super(context);
        paint=new Paint();
        paint.setColor(Color.GRAY);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.drawLine(10,10,20,20,paint);
    }

    @Override
    public void onReset() {
        Log.i("TAG","onReset") ;
    }

    @Override
    public void onPrepare() {
        Log.i("TAG","onPrepare") ;
    }

    @Override
    public void onRefreshing() {
        Log.i("TAG","onRefreshing") ;
    }

    @Override
    public void onMove(float offSet, float sumOffSet) {

    }

    @Override
    public boolean onRelease() {
        return false;
    }

    @Override
    public void refreshComplete() {

    }

    @Override
    public View getHeaderView() {
        return null;
    }

    @Override
    public int getVisibleHeight() {
        return 0;
    }
}
