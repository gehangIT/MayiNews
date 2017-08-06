package com.mayinews.mv.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.aliyun.vodplayer.media.AliyunPlayAuth;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.aliyun.vodplayerview.utils.ScreenUtils;
import com.aliyun.vodplayerview.widget.AliyunScreenMode;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.bumptech.glide.Glide;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.mayinews.mv.R;
import com.mayinews.mv.home.adapter.VideoDetailAdapter;
import com.mayinews.mv.home.bean.ItemVideoBean;
import com.mayinews.mv.home.bean.VideoList;
import com.mayinews.mv.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;


/**
 * Created by gary on 2017/6/28 0028.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class AliVideoPlayer extends Activity {
    private AliyunVodPlayerView mAliyunVodPlayerView;
    private LRecyclerView lrecyclerView;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    private VideoList.DataBean datas; //传递过来的item视频bean
    private String playAuth;//播放凭证，有失效时间
    private String videoId;//视频的vid

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alivideo);
        lrecyclerView = (LRecyclerView) findViewById(R.id.lrecyclerView);
        Intent intent = getIntent();

        Bundle bundle = intent.getBundleExtra("bundle");
        datas = bundle.getParcelable("dataBean");


        //找到播放器对象
        mAliyunVodPlayerView = (AliyunVodPlayerView) findViewById(R.id.video_view);
        mAliyunVodPlayerView.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                mAliyunVodPlayerView.start();
            }
        });
        mAliyunVodPlayerView.enableNativeLog();

        OkHttpUtils.get().url(Constants.GETPLAYAUTH+datas.getVid()).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                ItemVideoBean bean = JSON.parseObject(response, ItemVideoBean.class);
                ItemVideoBean.DataBean data = bean.getData();
                 playAuth = data.getPlayAuth();
                ItemVideoBean.DataBean.VideoMetaBean videoMeta = data.getVideoMeta();
                 videoId = videoMeta.getVideoId();
                /**
                 * 根据播放凭证方式播放
                 */
                AliyunPlayAuth.AliyunPlayAuthBuilder aliyunAuthInfoBuilder = new AliyunPlayAuth.AliyunPlayAuthBuilder();
                aliyunAuthInfoBuilder.setPlayAuth(playAuth);   //播放凭证
                aliyunAuthInfoBuilder.setVid(videoId);       //视频vid
                aliyunAuthInfoBuilder.setQuality(IAliyunVodPlayer.QualityValue.QUALITY_ORIGINAL);
                mAliyunVodPlayerView.setAuthInfo(aliyunAuthInfoBuilder.build());

            }
        });

        initData();

        /**
         * 本地播放
         */


////
//        AliyunLocalSource.AliyunLocalSourceBuilder aliyunLocalSourceBuilder = new AliyunLocalSource.AliyunLocalSourceBuilder();
//        aliyunLocalSourceBuilder.setSource("http://vfx.mtime.cn/Video/2017/06/25/mp4/170625140544387501.mp4");
//        mAliyunVodPlayerView.setLocalSource(aliyunLocalSourceBuilder.build());
//        initData();



    }

    private void initData() {


        Glide.with(this)
                .load("http://p.qpic.cn/videoyun/0/2449_bfbbfa3cea8f11e5aac3db03cda99974_1/640");

        lrecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        VideoDetailAdapter adapter = new VideoDetailAdapter(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.default_divider_height)

                .setColorResource(R.color.lightgray)
                .build();
        lrecyclerView.addItemDecoration(divider);
        lrecyclerView.setAdapter(mLRecyclerViewAdapter);


    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mAliyunVodPlayerView != null) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {                //转为竖屏了。
                //显示状态栏
                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                //设置view的布局，宽高之类
                ViewGroup.LayoutParams aliVcVideoViewLayoutParams = mAliyunVodPlayerView.getLayoutParams();
                aliVcVideoViewLayoutParams.height = (int) (ScreenUtils.getWight(this) * 9.0f / 16);
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;

                //设置为小屏状态
                mAliyunVodPlayerView.changeScreenMode(AliyunScreenMode.Small);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {                //转到横屏了。
                //隐藏状态栏
                this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
                //设置view的布局，宽高
                ViewGroup.LayoutParams aliVcVideoViewLayoutParams = mAliyunVodPlayerView.getLayoutParams();
                aliVcVideoViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;

                //设置为全屏状态
                mAliyunVodPlayerView.changeScreenMode(AliyunScreenMode.Full);
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onResume();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onStop();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onDestroy();
        }
    }
}
