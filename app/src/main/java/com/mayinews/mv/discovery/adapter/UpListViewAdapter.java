package com.mayinews.mv.discovery.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mayinews.mv.R;

/**
 * Created by gary on 2017/6/25 0025.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class UpListViewAdapter extends BaseAdapter {
    private Context context;

    public UpListViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_update_author,parent,false);
             viewHolder=new ViewHolder();
            viewHolder.authorIcon= (ImageView) convertView.findViewById(R.id.authorIcon);
            viewHolder.authorName= (TextView) convertView.findViewById(R.id.authorName);
            viewHolder.authorSign= (TextView) convertView.findViewById(R.id.authorSign);
            viewHolder.recyclerView= (RecyclerView) convertView.findViewById(R.id.recyclerView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        UpItemRcAdapter adapter = new UpItemRcAdapter(context);
        viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        viewHolder.recyclerView.setAdapter(adapter);
        return convertView;
    }

    class ViewHolder{
        ImageView authorIcon;
        TextView authorName;
        TextView authorSign;
        RecyclerView recyclerView;
    }
}
