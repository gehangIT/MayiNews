package com.mayinews.mv.home.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.mv.R;
import com.mayinews.mv.home.bean.NewestMediaItem;
import com.mayinews.mv.home.bean.VideoList;
import com.mayinews.mv.home.bean.VideoLists;
import com.mayinews.mv.utils.ScreenUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;
import java.util.zip.Inflater;

/**
 * Created by gary on 2017/6/15 0015.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class MyRcAdapter extends RecyclerView.Adapter<MyRcAdapter.MyViewHolder> {

    private Context context;
    private List<VideoLists.ResultBean> datas;
    private LayoutInflater inflater;


    public MyRcAdapter(Context context, List<VideoLists.ResultBean> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);

    }

    /**
     * 创建ViewHolder
     *
     * @param viewGroup
     * @param i
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View view = View.inflate(context, R.layout.home_recycler_item,null);
        View view = inflater.inflate(R.layout.home_recycler_item, viewGroup, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);


        return myViewHolder;
    }

    /**
     * 绑定数据
     *
     * @param myViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
   VideoLists.ResultBean  dataBean = datas.get(i);
        //用其它图片作为缩略图
        DrawableRequestBuilder<Integer> thumbnailRequest = Glide
                .with(context)
                .load(R.drawable.default_icon_head);

        ViewGroup.LayoutParams layoutParams = myViewHolder.imageView.getLayoutParams();


        Glide.with(context).load(buildGlideUrl(dataBean.getCover())).into(myViewHolder.imageView);
        myViewHolder.title.setText(dataBean.getTitle());
        SimpleDateFormat format=new SimpleDateFormat("mm:ss");
        double v = Double.parseDouble(dataBean.getDuration()) * 1000;
        String duration = format.format(v);
        myViewHolder.duration.setText(duration);
        myViewHolder.playTimes.setText(dataBean.getView());
        String user = dataBean.getNickname();
         myViewHolder.userName.setText(user);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://www.mayinews.com").build());
        }
}

class MyViewHolder extends RecyclerView.ViewHolder {

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

}