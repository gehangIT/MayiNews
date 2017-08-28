package com.mayinews.mv.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.mv.JNI;
import com.mayinews.mv.MyApplication;
import com.mayinews.mv.R;
import com.mayinews.mv.attention.activity.PersonVideosActivity;
import com.mayinews.mv.attention.bean.SubscribeLists;
import com.mayinews.mv.user.activity.LoginActivity;
import com.mayinews.mv.user.bean.SubscribeBean;
import com.mayinews.mv.utils.Constants;
import com.mayinews.mv.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * Created by 盖航_ on 2017/8/22.
 */

public class SubscribeAdapter extends RecyclerView.Adapter<SubscribeAdapter.MyViewHolder> {

     private Context context;
     private List<SubscribeBean.ResultBean> result=new ArrayList<>();
     private boolean isSub=false; //标记是否订阅

    public SubscribeAdapter(Context context) {
        this.context=context;

    }


    public void addData(List<SubscribeBean.ResultBean> result){
        this.result = result;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                context).inflate(R.layout.subscribe_item, parent,
                false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final SubscribeBean.ResultBean resultBean = result.get(position);
        holder.userName.setText(resultBean.getNickname());
        holder.userNick.setText(resultBean.getDesc());
        Glide.with(context).load(buildGlideUrl(resultBean.getAvatar())).into(holder.userIcon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPersonVideoActivity(position);
            }
        });

        holder.subscribe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {

                final String status = (String) SPUtils.get(context, MyApplication.LOGINSTATUES, "0");
                if(status.equals("1")){
                    String uid = (String) SPUtils.get(context, MyApplication.USERUID, "");
                    String key = new JNI().getString();
                    String token = (String) SPUtils.get(context, key, "");
                    OkHttpUtils.post().url(Constants.POSTSUBSCRIBE+uid).addHeader("Authorization","Bearer "+token).addParams("follow",resultBean.getUid())
                            .build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(context, "系统错误,请稍后重试", Toast.LENGTH_SHORT).show();
                            Log.i("TAG","错"+e.getMessage());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            JSONObject jsonObject = null;

                            try {
                                jsonObject = new JSONObject(response);
                                int status1 = jsonObject.optInt("status");
                                if(status1==200){
                                    if(!isSub){
                                        isSub=true;
                                        Toast.makeText(context, "订阅成功", Toast.LENGTH_SHORT).show();
                                        holder.subscribe.setText("已订阅");
                                    }else{
                                        isSub=false;
                                        Toast.makeText(context, "取消订阅成功", Toast.LENGTH_SHORT).show();
                                        holder.subscribe.setText(  "未订阅");

                                    }

                                }else{
                                    Toast.makeText(context, "系统错误,请稍后重试", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                }else{
                    context.startActivity(new Intent(context, LoginActivity.class));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{

        CircleImageView userIcon;
        TextView userName;
        TextView userNick;
        CheckBox subscribe;
        public MyViewHolder(View itemView) {
            super(itemView);
            userIcon = (CircleImageView) itemView.findViewById(R.id.userIcon);
            userName = (TextView) itemView.findViewById(R.id.userName);
            userNick = (TextView) itemView.findViewById(R.id.userNick);
            subscribe = (CheckBox) itemView.findViewById(R.id.subscribe);

        }
    }

    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://www.mayinews.com").build());
        }
    }


    //打开个人视频界面
    private void startPersonVideoActivity(int pos) {
        Intent intent=new Intent(context,PersonVideosActivity.class);
        SubscribeBean.ResultBean resultBean = result.get(pos);
        String uid = resultBean.getUid();  //当前的uid
        String name = resultBean.getNickname();  //当前的name
        intent.putExtra("uid",uid);
        intent.putExtra("nickName",name);
        Log.i("tag","uid="+uid);
        context.startActivity(intent);

    }
}
