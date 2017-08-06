package com.mayinews.mv.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.mayinews.mv.R;
import com.mayinews.mv.base.BaseFragment;
import com.mayinews.mv.home.activity.NoskinVpActivity;
import com.mayinews.mv.home.adapter.MyRcAdapter;
import com.mayinews.mv.home.bean.VideoList;
import com.mayinews.mv.utils.Constants;
import com.mayinews.mv.utils.NetUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by gary on 2017/6/14 0014.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class SiftFragment extends BaseFragment {

    private LRecyclerView lRecyclerView;
    private MyRcAdapter madapter;
    private ProgressBar progressBar;
    private LinearLayout check_linear;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;
    private  List<VideoList.DataBean> datas=new ArrayList();
    List<VideoList.DataBean> data;
    //列表的总页数
    private int totalPages;
    //当前的页数
    private int currentPage=1;
    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.sift_fragment, null);
        lRecyclerView = (LRecyclerView) view.findViewById(R.id.lrecyclerview);
        progressBar = (ProgressBar) view.findViewById(R.id.head_progressBar);
        check_linear = (LinearLayout) view.findViewById(R.id.check_linear);
        check_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNetState();
            }
        });
        return  view;
    }
    private void setListener() {

        lRecyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {
            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {

            }

            @Override
            public void onScrolled(int distanceX, int distanceY) {

            }

            @Override
            public void onScrollStateChanged(int state) {

            }
        });
        /**
         * 下拉刷新
         */
        lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                boolean connected = NetUtils.isConnected(mContext);
                if(connected){
                    check_linear.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    isRefresh=true;
//                    getNetState();

                }else{



                    progressBar.setVisibility(View.GONE);
                    //提示网不给力，但是还是有视频页面
                    Toast.makeText(mContext,"网络不给力，请检查网络设置",Toast.LENGTH_SHORT).show();
                    mLRecyclerViewAdapter.notifyDataSetChanged();

                }


            }
        });
        /**
         * 加载更多
         */
        lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isLoadMore = true;
//                if(currentPage<totalPages){
//                    currentPage++;
//                    getNetState();
//                }else{
//
//                }


                if (currentPage < totalPages) {
                    // loading data
                    currentPage++;
                    getNetState();
                } else {
                    Toast.makeText(mContext, "已经全部加载完", Toast.LENGTH_SHORT).show();
                    lRecyclerView.refreshComplete(datas.size());
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });


        /**
         * 点击事件
         */
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //进入播放视频的页面
                VideoList.DataBean dataBean = datas.get(position);
//                Intent intent=new Intent(getActivity(),VideoDetailsActivity.class);
//                Intent intent=new Intent(getActivity(),AliVideoPlayer.class);
                Intent intent=new Intent(getActivity(),NoskinVpActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("dataBean",dataBean);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }


        });

    }

    @Override
    protected void initData() {

        getNetState();

    }

    /**
     * 根据网络状态去请求数据
     */
    private void getNetState() {
        boolean connected = NetUtils.isConnected(mContext);
        if(connected){
            check_linear.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            RequestNetData();
        }else{


            Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();
            //显示没有网的图片
            check_linear.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);



        }
    }

    private void RequestNetData() {
        Log.i("TAG", Constants.VIDEO_LIST+"/page/"+currentPage);
        OkHttpUtils.get().url(Constants.VIDEO_LIST+"/page/"+currentPage).build().execute(new SiftFragment.MyCallBack());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 请求网络毁掉
     */
    class MyCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            VideoList videoList = JSON.parseObject(response, VideoList.class);

            Log.i("TAG", response);
            int status = videoList.getStatus();
            if (status == 1) {
                totalPages = videoList.getPages();  //得到数据的总页数

                data = videoList.getData();


                datas.addAll(data);
                if (null != datas && datas.size() > 0) {
                    SolveNetData();


                } else {
                    //提示没有数据

                }

            } else {
                //请求出错----提示信息请重试
                String message = videoList.getMessage();  //报错信息

            }


        }

        /**
         * 处理网络数据
         */
        private void SolveNetData() {

            Log.i("TAG","isLoadMore"+isLoadMore);

            if (isRefresh || isLoadMore) {
                isRefresh = false;
                isLoadMore = false;

                lRecyclerView.refreshComplete(10);

                mLRecyclerViewAdapter.notifyDataSetChanged();


            }else{
                madapter = new MyRcAdapter(mContext, datas);
                mLRecyclerViewAdapter = new LRecyclerViewAdapter(madapter);
                lRecyclerView.setAdapter(mLRecyclerViewAdapter);
                lRecyclerView.setPullRefreshEnabled(false);
                lRecyclerView.setLoadMoreEnabled(true);
                progressBar.setVisibility(View.GONE);
                GridLayoutManager manager = new GridLayoutManager(mContext, 2);
                //setLayoutManager
                final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager( 2, StaggeredGridLayoutManager.VERTICAL);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                //防止item位置互换
                layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);


                lRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        //防止第一行到顶部有空白区域
                        layoutManager.invalidateSpanAssignments();
                    }
                });
                lRecyclerView.setLayoutManager(layoutManager);
                setListener();
            }
            progressBar.setVisibility(View.GONE);

        }
    }

}
