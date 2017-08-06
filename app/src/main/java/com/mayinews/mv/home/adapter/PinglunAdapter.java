package com.mayinews.mv.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mayinews.mv.R;

/**
 * Created by gary on 2017/6/26 0026.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class PinglunAdapter extends BaseAdapter {
    private Context context;

    public PinglunAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pinglun, parent, false);
        }

        return convertView;
    }

    class ViewHolder{


    }
}
