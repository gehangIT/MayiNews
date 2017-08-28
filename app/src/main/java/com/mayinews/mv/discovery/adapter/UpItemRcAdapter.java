package com.mayinews.mv.discovery.adapter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.mv.R;
import com.mayinews.mv.attention.activity.PersonVideosActivity;
import com.mayinews.mv.attention.bean.UserVideo;
import com.mayinews.mv.home.activity.NoskinVpActivity;
import com.mayinews.mv.home.bean.VideoLists;

import java.util.List;

/**
 * Created by gary on 2017/6/25 0025.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class UpItemRcAdapter extends RecyclerView.Adapter <UpItemRcAdapter.MyViewHolder>{
    private Context context;
    private List<VideoLists.ResultBean> resultBeen;
    public UpItemRcAdapter(Context context, List<VideoLists.ResultBean> resultBeen) {
        this.context = context;
        this.resultBeen=resultBeen;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_upauthor_recycler, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final VideoLists.ResultBean resultBean = resultBeen.get(position);
        Glide.with(context).load(buildGlideUrl(resultBean.getCover())).into(holder.videoImageView);
        holder.videoDesc.setText(resultBean.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //进入播放视频的页面
                Intent intent = new Intent(context, NoskinVpActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("dataBean", resultBean);
                intent.putExtra("bundle", bundle);
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.activity_open, R.anim.activity_backgroud);
            }
        });
}

    @Override
    public int getItemCount() {
        return resultBeen.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView videoImageView;
        TextView  videoDesc;



        public MyViewHolder(View itemView) {
            super(itemView);
            videoImageView= (ImageView) itemView.findViewById(R.id.videoImageView);
            videoDesc = (TextView) itemView.findViewById(R.id.videoDesc);

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
