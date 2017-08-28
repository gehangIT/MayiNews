package com.mayinews.mv.attention.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonHeader;
import com.mayinews.mv.R;
import com.mayinews.mv.attention.adapter.VideoAdapter;
import com.mayinews.mv.attention.bean.UserVideo;
import com.mayinews.mv.home.activity.NoskinVpActivity;
import com.mayinews.mv.home.bean.VideoLists;
import com.mayinews.mv.utils.Constants;
import com.mayinews.mv.utils.NetUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class PersonVideosActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_back;
    private LRecyclerView recyclerView;

    private String uid;//当前用户的uid
    private String name;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int pageNum = 0;  //当前请求页
    private VideoAdapter adapter;
//    List<UserVideo.ResultBean> videos = new ArrayList<>();//视频集合
    List<VideoLists.ResultBean> videos = new ArrayList<>();//视频集合
    private boolean isLoadmore=false;//是否加载更多了

    private LinearLayout ll_error;
    private ProgressBar pb_loading;
    private LinearLayout check_linear;
    private LinearLayout no_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_videos);
        initView();
        setListener();
        initData();
    }

    private void setListener() {
        iv_back.setOnClickListener(this);


        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                isLoadmore=true;
                boolean connected = NetUtils.isConnected(PersonVideosActivity.this);
                if(connected){
                    requestData();
                }else{
                    pb_loading.setVisibility(View.GONE);
                    //提示网不给力，但是还是有视频页面
                    Toast.makeText(PersonVideosActivity.this, "网络不给力，请检查网络设置", Toast.LENGTH_SHORT).show();
                    recyclerView.refreshComplete(10);
                    mLRecyclerViewAdapter.notifyDataSetChanged();

                }

            }
        });

        check_linear.setOnClickListener(this);



    }

    private void initData() {
        uid = getIntent().getStringExtra("uid");
        name = getIntent().getStringExtra("nickName");
        adapter = new VideoAdapter(this, name);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);

        recyclerView.setAdapter(mLRecyclerViewAdapter);

        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //进入播放视频的页面
                VideoLists.ResultBean dataBean = videos.get(position);
                Intent intent = new Intent(PersonVideosActivity.this, NoskinVpActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("dataBean", dataBean);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_open, R.anim.activity_backgroud);
            }
        });
        getData();



    }

    private void getData() {
        boolean connected = NetUtils.isConnected(this);
        if (connected) {
            check_linear.setVisibility(View.GONE);
            pb_loading.setVisibility(View.VISIBLE);
            //根据uid来请求视频
            requestData();;
        }else{
            Toast.makeText(PersonVideosActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
            check_linear.setVisibility(View.VISIBLE);
            pb_loading.setVisibility(View.GONE);

        }
    }

    //联网请求数据
    private void requestData() {
        pb_loading.setVisibility(View.VISIBLE);
        OkHttpUtils.get().url(Constants.GETUSERVIDEO + uid + "/page/" + pageNum).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                    ll_error.setVisibility(View.VISIBLE);
                    pb_loading.setVisibility(View.GONE);
                   recyclerView.setVisibility(View.GONE);
                   Log.i("TAG","出错"+e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {

                ll_error.setVisibility(View.GONE);
//                UserVideo userVideo = JSON.parseObject(response, UserVideo.class);
                VideoLists userVideo = JSON.parseObject(response, VideoLists.class);

                if (userVideo.getCount() != 0) {
//                    List<UserVideo.ResultBean> result = userVideo.getResult();
                    List<VideoLists.ResultBean> result = userVideo.getResult();

                    videos.addAll(result);
                    adapter.setData(videos);
//                    adapter.notifyDataSetChanged();
                    recyclerView.refreshComplete(10);
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                    pb_loading.setVisibility(View.GONE);

                } else {

                    if(isLoadmore){
                        Toast.makeText(PersonVideosActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                        recyclerView.refreshComplete(10);
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                    }else {
                        no_video.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    pb_loading.setVisibility(View.GONE);

                }

            }
        });

    }

    private void initView() {
        check_linear = (LinearLayout)findViewById(R.id.check_linear);
        iv_back = (ImageView) findViewById(R.id.finish_back);
        recyclerView = (LRecyclerView) findViewById(R.id.recyclerView);
        ll_error = (LinearLayout)findViewById(R.id.ll_error);
        pb_loading = (ProgressBar)findViewById(R.id.pb_loading);
        no_video = (LinearLayout)findViewById(R.id.no_video);
        recyclerView.setPullRefreshEnabled(false);

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //防止item位置互换
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //防止第一行到顶部有空白区域
                layoutManager.invalidateSpanAssignments();
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        //添加头



    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.finish_back:
                finish();
                break;
            case R.id.check_linear:

                getData();
                break;
        }

    }
}
