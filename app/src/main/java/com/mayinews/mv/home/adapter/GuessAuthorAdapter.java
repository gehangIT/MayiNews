package com.mayinews.mv.home.adapter;

import android.content.Context;
import android.media.Image;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.mv.R;
import com.mayinews.mv.home.bean.VideoLists;
import com.mayinews.mv.utils.Number2Thousand;
import com.mayinews.mv.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.List;

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
    private List<VideoLists.ResultBean> guessLikeVideos; //猜你喜欢相关的视频

    public GuessAuthorAdapter(Context context,List<VideoLists.ResultBean> guessLikeVideos) {
        this.context = context;
        inflater=LayoutInflater.from(context);
        this.guessLikeVideos=guessLikeVideos;
    }

    @Override
    public int getCount() {
        return guessLikeVideos.size();
    }

    @Override
    public Object getItem(int position) {
        return guessLikeVideos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VideoLists.ResultBean resultBean = guessLikeVideos.get(position);
        ViewHolder viewHolder=null;
        if(convertView==null){
             convertView=inflater.inflate(R.layout.item_guess_author,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.videoTitle= (TextView) convertView.findViewById(R.id.title);
            viewHolder.authorName= (TextView) convertView.findViewById(R.id.authorName);
            viewHolder.videoImageView= (ImageView) convertView.findViewById(R.id.videoImageView);
            viewHolder.videoDuration= (TextView) convertView.findViewById(R.id.videoDuration);
            viewHolder.viewCount= (TextView) convertView.findViewById(R.id.viewCount);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        viewHolder.videoTitle.setText(resultBean.getTitle());
        viewHolder.authorName.setText(resultBean.getUser());
        viewHolder.viewCount.setText(Number2Thousand.number2Thousand(resultBean.getView())+"次播放");
        Glide.with(context).load(buildGlideUrl(resultBean.getCover())).into(viewHolder.videoImageView);
        SimpleDateFormat format=new SimpleDateFormat("mm:ss");
        double v = Double.parseDouble(resultBean.getDuration()) * 1000;
        String duration = format.format(v);
        viewHolder.videoDuration.setText(duration);
        return convertView;
    }
    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://www.mayinews.com").build());
        }
    }

    class ViewHolder {
        TextView videoTitle;
        TextView authorName;
        TextView viewCount;
        ImageView videoImageView;
        TextView videoDuration;


    }



    }
