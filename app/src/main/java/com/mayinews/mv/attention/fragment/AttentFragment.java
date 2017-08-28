package com.mayinews.mv.attention.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.fastjson.JSON;
import com.aliyun.common.utils.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonHeader;
import com.mayinews.mv.JNI;
import com.mayinews.mv.MyApplication;
import com.mayinews.mv.R;
import com.mayinews.mv.attention.activity.PersonVideosActivity;
import com.mayinews.mv.attention.bean.SubscribeLists;
import com.mayinews.mv.attention.bean.UserVideo;
import com.mayinews.mv.base.BaseFragment;
import com.mayinews.mv.discovery.activity.PublishActivity;
import com.mayinews.mv.discovery.adapter.MyDcAdapter;
import com.mayinews.mv.discovery.adapter.UpItemRcAdapter;
import com.mayinews.mv.home.bean.VideoLists;
import com.mayinews.mv.user.activity.LoginActivity;
import com.mayinews.mv.utils.Constants;
import com.mayinews.mv.utils.NetUtils;
import com.mayinews.mv.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;


/**
 * Created by gary on 2017/6/14 0014.
 * 微信:GH-12-10
 * QQ:1683701403
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class AttentFragment extends BaseFragment {


    private RecyclerView mRecyclerView;
    private TextView publish;
    private List<DelegateAdapter.Adapter> adapters;
    private List<SubscribeLists.ResultBean> result;  //推荐订阅作者集合


//    private List<List<UserVideo.ResultBean>> videoLists = new ArrayList<>();// 三个推荐作者的视频
    private List<List<VideoLists.ResultBean>> videoLists = new ArrayList<>();// 三个推荐作者的视频
    private VirtualLayoutManager layoutManager;

    private LinearLayout ll_error;//发生错误的时候显示
    private LinearLayout check_linear;//没有网的时候
    private ProgressBar pb_loading;
    private String status;   //登录的状态
    private String uid;      //用户id
    private String token;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.discovery, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_atten);
        publish = (TextView) view.findViewById(R.id.publish);
        ll_error = (LinearLayout) view.findViewById(R.id.ll_error);
        pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);
        check_linear = (LinearLayout) view.findViewById(R.id.check_linear);
        setListener();
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
//        mRecyclerView.setNestedScrollingEnabled(false);  //不允许嵌套的滑动
        //1


        layoutManager = new VirtualLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

         status = (String) SPUtils.get(mContext,MyApplication.LOGINSTATUES,"0");
        uid = (String) SPUtils.get(mContext,MyApplication.USERUID,"0");
        String key = new JNI().getString();
        token = (String) SPUtils.get(mContext,key,"");

        getData();
         return view;
    }

    private void getData() {
        boolean connected = NetUtils.isConnected(getActivity());
        if(connected){
            check_linear.setVisibility(View.GONE);
            pb_loading.setVisibility(View.VISIBLE);
            ll_error.setVisibility(View.GONE);
            requestData();

        }else{
            check_linear.setVisibility(View.VISIBLE);
            pb_loading.setVisibility(View.GONE);


        }
    }


    private void requestData() {

        OkHttpUtils.get().url(Constants.GETSUBSCRIBELISTS).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ll_error.setVisibility(View.VISIBLE);
                pb_loading.setVisibility(View.GONE);
                Log.i("TAG","关注错误"+e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {

                SubscribeLists subscribeLists = JSON.parseObject(response, SubscribeLists.class);
                result = subscribeLists.getResult();
                //请求对应作者的详细作品

                OkHttpUtils.get().url(Constants.GETUSERVIDEO + result.get(3).getUid()).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ll_error.setVisibility(View.VISIBLE);
                        pb_loading.setVisibility(View.GONE);
                        Log.i("TAG","关注错误1"+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        VideoLists userVideo = JSON.parseObject(response, VideoLists.class);
                        List<VideoLists.ResultBean> bean1 = userVideo.getResult();
                        videoLists.add(bean1);
                        OkHttpUtils.get().url(Constants.GETUSERVIDEO + result.get(4).getUid()).build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                     ll_error.setVisibility(View.VISIBLE);
                                pb_loading.setVisibility(View.GONE);
                                Log.i("TAG","关注错误2"+e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                VideoLists userVideo = JSON.parseObject(response, VideoLists.class);
                                List<VideoLists.ResultBean> bean2 = userVideo.getResult();
                                videoLists.add(bean2);
                                OkHttpUtils.get().url(Constants.GETUSERVIDEO + result.get(5).getUid()).build().execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        ll_error.setVisibility(View.VISIBLE);
                                        pb_loading.setVisibility(View.GONE);
                                        Log.i("TAG","关注错误3"+e.getMessage());
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        VideoLists userVideo = JSON.parseObject(response, VideoLists.class);
                                        List<VideoLists.ResultBean> bean3 = userVideo.getResult();
                                        videoLists.add(bean3);

                                        //处理数据

                                        ResultData(result, videoLists);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    private void setListener() {
        //发布的监听
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PublishActivity.class);
                startActivity(intent);
            }
        });

        check_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }


    private void ResultData(final List<SubscribeLists.ResultBean> result, final List<List<VideoLists.ResultBean>> videoLists) {
        adapters = new LinkedList<>();
        adapters.add(new DelegateAdapter.Adapter() {
            @Override
            public LayoutHelper onCreateLayoutHelper() {
                //设置线性布局1
                LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
                linearLayoutHelper.setItemCount(1);
//                //设置间隔高度
                linearLayoutHelper.setMarginBottom(6);

                return linearLayoutHelper;
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


                return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.discovery_head, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 1;
            }


            class MyViewHolder extends RecyclerView.ViewHolder {

                public MyViewHolder(View itemView) {
                    super(itemView);
                }
            }
        });
        adapters.add(new DelegateAdapter.Adapter() {
            @Override
            public LayoutHelper onCreateLayoutHelper() {
                //设置线性布局2
                LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
                linearLayoutHelper.setItemCount(1);
//                //设置间隔高度
                linearLayoutHelper.setDividerHeight(1);
                linearLayoutHelper.setMarginBottom(1);

                return linearLayoutHelper;
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


                return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.hot_author_item, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 1;
            }


            class MyViewHolder extends RecyclerView.ViewHolder {

                public MyViewHolder(View itemView) {
                    super(itemView);
                }
            }
        });

        adapters.add(new DelegateAdapter.Adapter() {

            @Override
            public LayoutHelper onCreateLayoutHelper() {
                //设置线性布局3
                LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
                linearLayoutHelper.setItemCount(3);
//                //设置间隔高度
                linearLayoutHelper.setDividerHeight(1);
                linearLayoutHelper.setMarginBottom(6);

                return linearLayoutHelper;
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


                return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_hot_author, parent, false));
            }

            @Override
            public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

                SubscribeLists.ResultBean resultBean = result.get(position);
                final MyViewHolder myViewHolder = (MyViewHolder) holder;
                myViewHolder.authorName.setText(resultBean.getNickname());
                myViewHolder.authorSign.setText(resultBean.getDesc());
                Glide.with(mContext).load(buildGlideUrl(resultBean.getAvatar())).into(myViewHolder.authorIcon);
                myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         int pos = holder.getLayoutPosition()-2;
                          //打开作者的视频列表
                          startPersonVideoActivity(pos);

                     }
                 });
                myViewHolder.cb_sub.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                        if(status.equals("1")){

                            if(isChecked){
                                myViewHolder.cb_sub.setText("已订阅");
                                Toast.makeText(mContext, "订阅成功", Toast.LENGTH_SHORT).show();

                            }else{
                                myViewHolder.cb_sub.setText("＋订阅");
                                Toast.makeText(mContext, "取消订阅成功", Toast.LENGTH_SHORT).show();
                            }
                            postSub(isChecked, position);
                        }else{
                            myViewHolder.cb_sub.setChecked(false);
                            //跳转到登录界面
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }



                    }
                });

            }



            @Override
            public int getItemCount() {
                return 3;
            }


            class MyViewHolder extends RecyclerView.ViewHolder {
                CircleImageView authorIcon;
                TextView authorName;
                TextView authorSign;
                CheckBox cb_sub;

                public MyViewHolder(View itemView) {

                    super(itemView);
                    authorIcon = (CircleImageView) itemView.findViewById(R.id.authorIcon);
                    authorName = (TextView) itemView.findViewById(R.id.authorName);
                    authorSign = (TextView) itemView.findViewById(R.id.authorSign);
                    cb_sub = (CheckBox) itemView.findViewById(R.id.cb_sub);

                  }


            }
        });
        adapters.add(new DelegateAdapter.Adapter() {
            @Override
            public LayoutHelper onCreateLayoutHelper() {
                //设置线性布局4
                LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
                linearLayoutHelper.setItemCount(1);
//                //设置间隔高度
                linearLayoutHelper.setDividerHeight(1);
                linearLayoutHelper.setMarginBottom(1);

                return linearLayoutHelper;
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


                return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.update_author, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {




            }

            @Override
            public int getItemCount() {
                return 1;
            }


            class MyViewHolder extends RecyclerView.ViewHolder {

                public MyViewHolder(View itemView) {
                    super(itemView);
                }
            }
        });


        adapters.add(new DelegateAdapter.Adapter() {
            @Override
            public LayoutHelper onCreateLayoutHelper() {
                //设置线性布局5
                LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
                linearLayoutHelper.setItemCount(3);
//                //设置间隔高度
                linearLayoutHelper.setDividerHeight(1);
                linearLayoutHelper.setMarginBottom(6);

                return linearLayoutHelper;
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


                return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_update_author, parent, false));
            }

            @Override
            public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

                SubscribeLists.ResultBean resultBean = result.get(position + 3);

                UpItemRcAdapter adapter = new UpItemRcAdapter(mContext, videoLists.get(position));
                final MyViewHolder myholder = (MyViewHolder) holder;
                Glide.with(mContext).load(buildGlideUrl(resultBean.getAvatar())).into(myholder.authorIcon);
                myholder.authorName.setText(resultBean.getNickname());
                myholder.authorSign.setText(resultBean.getDesc());
                myholder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                myholder.recyclerView.setAdapter(adapter);
                myholder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = myholder.getLayoutPosition()-3;
                        //打开作者的视频列表
                        startPersonVideoActivity(pos);
                    }
                });

                myholder.cb_subscribe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(status.equals("1")){

                            if(isChecked){

                                myholder.cb_subscribe.setText("已订阅");
                                Toast.makeText(mContext, "订阅成功", Toast.LENGTH_SHORT).show();

                            }else{
                                myholder.cb_subscribe.setText("＋订阅");
                                Toast.makeText(mContext, "取消订阅成功", Toast.LENGTH_SHORT).show();
                            }
                            Log.i("TAG","1234="+position+3);
                            postSub(isChecked, position+3);
                        }else{
                            myholder.cb_subscribe.setChecked(false);
                            //跳转到登录界面
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }




                    }
                });


            }

            @Override
            public int getItemCount() {
                return 3;
            }


            class MyViewHolder extends RecyclerView.ViewHolder {

                CircleImageView authorIcon;//头像
                TextView authorName;   //昵称
                TextView authorSign;   //签名
                CheckBox cb_subscribe;//订阅
                RecyclerView recyclerView; //视频列表

                public MyViewHolder(View itemView) {
                    super(itemView);
                    authorIcon = (CircleImageView) itemView.findViewById(R.id.authorIcon);
                    authorName = (TextView) itemView.findViewById(R.id.authorName);
                    authorSign = (TextView) itemView.findViewById(R.id.authorSign);
                    cb_subscribe = (CheckBox) itemView.findViewById(R.id.cb_subscribe);
                    recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);

                }
            }
        });

        adapters.add(new DelegateAdapter.Adapter() {
            @Override
            public LayoutHelper onCreateLayoutHelper() {
                //设置线性布局6
                LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
                linearLayoutHelper.setItemCount(1);
//                //设置间隔高度
                linearLayoutHelper.setDividerHeight(1);
                linearLayoutHelper.setMarginBottom(1);

                return linearLayoutHelper;
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


                return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.other_author_item, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {



            }

            @Override
            public int getItemCount() {
                return 1;
            }


            class MyViewHolder extends RecyclerView.ViewHolder {

                public MyViewHolder(View itemView) {
                    super(itemView);
                }
            }
        });
        adapters.add(new DelegateAdapter.Adapter() {
            @Override
            public LayoutHelper onCreateLayoutHelper() {
                //设置线性布局6
                LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
                linearLayoutHelper.setItemCount(12);
//                //设置间隔高度
                linearLayoutHelper.setDividerHeight(1);
                linearLayoutHelper.setMarginBottom(1);

                return linearLayoutHelper;
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


                return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_hot_author, parent, false));
            }

            @Override
            public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
                SubscribeLists.ResultBean resultBean = result.get(position + 6);
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                myViewHolder.authorName.setText(resultBean.getNickname());
                myViewHolder.authorSign.setText(resultBean.getDesc());
                Glide.with(mContext).load(buildGlideUrl(resultBean.getAvatar())).into(myViewHolder.authorIcon);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition()-4;
                        //打开作者的视频列表
                        startPersonVideoActivity(pos);
                    }
                });
                ((MyViewHolder)holder).cb_sub.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if(status.equals("1")){

                            if(isChecked){

                                ((MyViewHolder)holder).cb_sub.setText("已订阅");
                                Toast.makeText(mContext, "订阅成功", Toast.LENGTH_SHORT).show();

                            }else{
                                ((MyViewHolder)holder).cb_sub.setText("未订阅");
                                Toast.makeText(mContext, "取消订阅成功", Toast.LENGTH_SHORT).show();
                            }
                            Log.i("TAG","1234="+position+6);
                            postSub(isChecked, position+6);
                        }else{
                            ((MyViewHolder)holder).cb_sub.setChecked(false);
                            //跳转到登录界面
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }




                    }
                });



            }

            @Override
            public int getItemCount() {
                return 12;
            }


            class MyViewHolder extends RecyclerView.ViewHolder {
                CircleImageView authorIcon;
                TextView authorName;
                TextView authorSign;
                CheckBox cb_sub;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    authorIcon = (CircleImageView) itemView.findViewById(R.id.authorIcon);
                    authorName = (TextView) itemView.findViewById(R.id.authorName);
                    authorSign = (TextView) itemView.findViewById(R.id.authorSign);
                    cb_sub = (CheckBox) itemView.findViewById(R.id.cb_sub);

                }
            }
        });


        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, false);
        delegateAdapter.setAdapters(adapters);
        mRecyclerView.setAdapter(delegateAdapter);
        pb_loading.setVisibility(View.GONE);
    }
       //打开个人视频界面
     private void startPersonVideoActivity(int pos) {
        Intent intent=new Intent(getActivity(),PersonVideosActivity.class);
        SubscribeLists.ResultBean resultBean = result.get(pos);
        String uid = resultBean.getUid();  //当前的uid
        String name = resultBean.getNickname();  //当前的name
        intent.putExtra("uid",uid);
        intent.putExtra("nickName",name);
         Log.i("tag","uid="+uid);
        startActivity(intent);

    }

    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://www.mayinews.com").build());
        }
    }


    private void postSub(final boolean isChecked, int position) {
        //订阅的接口

        Log.i("TAG","1234"+position);


            //访问接口
            OkHttpUtils.post().url(Constants.POSTSUBSCRIBE+uid)
                    .addHeader("Authorization","Bearer "+token)
                    .addParams("follow",result.get(position).getUid())
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(String response, int id) {

                }
            });


    }


}
