package com.mayinews.mv.user.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.aliyun.common.utils.ToastUtil;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.mayinews.mv.JNI;
import com.mayinews.mv.MyApplication;
import com.mayinews.mv.R;
import com.mayinews.mv.attention.activity.PersonVideosActivity;
import com.mayinews.mv.user.adapter.SubscribeAdapter;
import com.mayinews.mv.user.bean.SubscribeBean;
import com.mayinews.mv.utils.Constants;
import com.mayinews.mv.utils.NetUtils;
import com.mayinews.mv.utils.SPUtils;
import com.mayinews.mv.utils.StringUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class SubActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_back;
    private LRecyclerView lrecyclerView;
    private LinearLayout no_subscribe;
    private LinearLayout ll_error;   //出错
    private LinearLayout check_linear;   //没有网
    private ProgressBar pb_loading;
    private int page=0;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private List<SubscribeBean.ResultBean> result=new ArrayList<>();
    private SubscribeAdapter subscribeAdapter;
    private boolean isLoadmore=false;//是否上拉加载更多

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        initView();
        setOnListener();
        getData();
    }


    private void getData() {
        boolean connected = NetUtils.isConnected(this);
        if (connected) {
            check_linear.setVisibility(View.GONE);
            pb_loading.setVisibility(View.VISIBLE);
            //根据uid来请求视频
            initData();
        }else{
            check_linear.setVisibility(View.VISIBLE);
            pb_loading.setVisibility(View.GONE);

        }
    }

    private void initView() {
        iv_back = (ImageView)findViewById(R.id.iv_back);
        no_subscribe = (LinearLayout)findViewById(R.id.no_subscribe);
        lrecyclerView = (LRecyclerView)findViewById(R.id.lrecyclerView);
        lrecyclerView.setPullRefreshEnabled(false);
        lrecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        ll_error = (LinearLayout)findViewById(R.id.ll_error);
       check_linear = (LinearLayout)findViewById(R.id.check_linear);
        pb_loading = (ProgressBar)findViewById(R.id.pb_loading);
        check_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });


        //显示订阅的
        subscribeAdapter = new SubscribeAdapter(SubActivity.this);
         lRecyclerViewAdapter = new LRecyclerViewAdapter(subscribeAdapter);

        DividerDecoration divider = new DividerDecoration.Builder(SubActivity.this)
                .setHeight(R.dimen.default_divider_height)
                .setColorResource(R.color.gh_gray)
                .build();
        lrecyclerView.addItemDecoration(divider);
        lrecyclerView.setAdapter(lRecyclerViewAdapter);

    }
    private void setOnListener() {
        iv_back.setOnClickListener(this);

        lrecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                isLoadmore=true;
                boolean connected = NetUtils.isConnected(SubActivity.this);
                if(connected){
                    initData();
                }else{
                    pb_loading.setVisibility(View.GONE);
                    //提示网不给力，但是还是有视频页面
                    Toast.makeText(SubActivity.this, "网络不给力，请检查网络设置", Toast.LENGTH_SHORT).show();
                    lrecyclerView.refreshComplete(20);
                    lRecyclerViewAdapter.notifyDataSetChanged();

                }

            }
        });

    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_back:
                     finish();
                    break;

            }
    }


    private void initData() {
        String  status = (String) SPUtils.get(this, MyApplication.LOGINSTATUES, "0");
        if(!status.equals("1")){
              // 跳转到登录页面
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }else{
            JNI jni = new JNI();
            String key = jni.getString();
            String token = (String) SPUtils.get(this,key,"");
            OkHttpUtils.get().url(Constants.GETSUBSCRIBE+page).addHeader("Authorization",""+"Bearer "+token).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    ll_error.setVisibility(View.VISIBLE);
                    pb_loading.setVisibility(View.GONE);
                }

                @Override
                public void onResponse(String response, int id) {
                    SubscribeBean subscribeBean = JSON.parseObject(response, SubscribeBean.class);
                    int count = subscribeBean.getCount();

                    List<SubscribeBean.ResultBean> res = subscribeBean.getResult();
                    if(count==0){

                        if(isLoadmore){
                            Toast.makeText(SubActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                            lrecyclerView.refreshComplete(20);
                            lRecyclerViewAdapter.notifyDataSetChanged();
                        }else{
                            //显示没有订阅的
                            lrecyclerView.setVisibility(View.GONE);
                            no_subscribe.setVisibility(View.VISIBLE);
                            pb_loading.setVisibility(View.GONE);
                        }


                    }else{
                        result.addAll(res);
                        subscribeAdapter.addData(result);
                        lrecyclerView.refreshComplete(20);
                        lRecyclerViewAdapter.notifyDataSetChanged();

                        pb_loading.setVisibility(View.GONE);
                    }
                }
            });
        }

    }
}
