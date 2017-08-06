package com.mayinews.mv.home.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mayinews.mv.R;

/**
 * Created by gary on 2017/6/26 0026.
 * 微信:GH-12-10
 * QQ:1683701403
 * 播放视频x详情界面的猜你喜欢的adapter
 *
 */

public class GuessAuthorAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;

    public GuessAuthorAdapter(Context context) {
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 10;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
             convertView=inflater.inflate(R.layout.item_guess_author,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.videoTitle= (TextView) convertView.findViewById(R.id.title);
            viewHolder.authorName= (TextView) convertView.findViewById(R.id.authorName);
            viewHolder.videoImageView= (ImageView) convertView.findViewById(R.id.videoImageView);
            viewHolder.videoDuration= (TextView) convertView.findViewById(R.id.videoDuration);
        }

        return convertView;
    }

    class ViewHolder {
       TextView videoTitle;
       TextView authorName;
        ImageView videoImageView;
       TextView videoDuration;


    }
}
