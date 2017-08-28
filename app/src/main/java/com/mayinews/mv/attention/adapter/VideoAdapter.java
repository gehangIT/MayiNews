package com.mayinews.mv.attention.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.mv.R;
import com.mayinews.mv.attention.bean.UserVideo;
import com.mayinews.mv.home.bean.VideoList;
import com.mayinews.mv.home.bean.VideoLists;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 盖航_ on 2017/8/24.
 * 视频列表的
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<VideoLists.ResultBean> videoList = new ArrayList<>();
    private String name;//视频作者
    public VideoAdapter(Context context,String name) {
        this.context = context;
        this.name=name;
        inflater=LayoutInflater.from(context);
    }

    public void setData(List<VideoLists.ResultBean> videoList){

        this.videoList=videoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.home_recycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        VideoLists.ResultBean resultBean = videoList.get(position);
        //用其它图片作为缩略图
        DrawableRequestBuilder<Integer> thumbnailRequest = Glide
                .with(context)
                .load(R.drawable.default_icon_head);



        Glide.with(context).load(buildGlideUrl(resultBean.getCover())).into(holder.imageView);
        holder.title.setText(resultBean.getTitle());
        SimpleDateFormat format=new SimpleDateFormat("mm:ss");
        double v = Double.parseDouble(resultBean.getDuration()) * 1000;
        String duration = format.format(v);
        holder.duration.setText(duration);
        holder.playTimes.setText(resultBean.getView());
        holder.userName.setText(name);

    }


    @Override
    public int getItemCount() {
        return videoList.size();
    }


    class MyViewHolder  extends  RecyclerView.ViewHolder{

        ImageView imageView;
        TextView userName;
        TextView title;
        TextView duration;
        TextView playTimes;

        public MyViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.item_tv);
            imageView= (ImageView) itemView.findViewById(R.id.item_iv);
            userName = (TextView) itemView.findViewById(R.id.user);
            duration = (TextView) itemView.findViewById(R.id.tv_duration);
            playTimes = (TextView) itemView.findViewById(R.id.playTimes);


        }
    }


    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://www.mayinews.com").build());
        }
    }
}
