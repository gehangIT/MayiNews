package com.mayinews.mv.discovery.view;

/**
 * Created by gary on 2017/6/25 0025.
 * 微信:GH-12-10
 * QQ:1683701403
 */


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 作者：joy on 2015/11/27 10:23
 */
public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}