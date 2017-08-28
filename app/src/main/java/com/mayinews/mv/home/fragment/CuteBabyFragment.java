package com.mayinews.mv.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.github.jdsjlzx.ItemDecoration.GridItemDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.mayinews.mv.MyApplication;
import com.mayinews.mv.R;
import com.mayinews.mv.base.BaseFragment;
import com.mayinews.mv.home.activity.NoskinVpActivity;
import com.mayinews.mv.home.adapter.MyRcAdapter;
import com.mayinews.mv.home.bean.VideoLists;
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

public class CuteBabyFragment extends BaseFragment {
    private static final int CACLEPOP = 1;

    private static final int FINISHREFRESH = 2;
    private LRecyclerView lRecyclerView;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CACLEPOP:
                    popupWindow.dismiss();
                    break;
                case FINISHREFRESH:
                    lRecyclerView.refreshComplete(data.size());
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private MyRcAdapter madapter;
    private ProgressBar progressBar;
    private LinearLayout check_linear;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private boolean isPulldownRefresh = false;    //是否下拉刷新
    private boolean isLoadMore = true;   //是否上拉加载更多
    private boolean isRefresh = false;    //在页面时就刷新
    private List<VideoLists.ResultBean> datas = new ArrayList();
    List<VideoLists.ResultBean> data;
    int count;//视频的数量
    int status;//接口返回的状态
    //列表的总页数
    private int totalPages;
    //当前的页数
    private int currentPage_up = 1;  //上啦加载更多
    private PopupWindow popupWindow;//显示更新的视频条数

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.newest_fragment, null);

        lRecyclerView = (LRecyclerView) view.findViewById(R.id.lrecyclerview);
        progressBar = (ProgressBar) view.findViewById(R.id.head_progressBar);
        check_linear = (LinearLayout) view.findViewById(R.id.check_linear);
        check_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNetState();
            }
        });


        return view;
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
                isLoadMore = false;
                isRefresh = false;
                isPulldownRefresh = true;
                boolean connected = NetUtils.isConnected(mContext);
                if (connected) {
                    if (status == 1 && count != 0) {

                        getNetState();
                    } else {
                        Toast.makeText(mContext, "没有新的数据", Toast.LENGTH_SHORT).show();
                        lRecyclerView.refreshComplete(datas.size());
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                    }
                    check_linear.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);


                } else {


                    progressBar.setVisibility(View.GONE);
                    //提示网不给力，但是还是有视频页面
                    Toast.makeText(mContext, "网络不给力，请检查网络设置", Toast.LENGTH_SHORT).show();
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
                isRefresh = false;
                isPulldownRefresh = false;
                boolean connected = NetUtils.isConnected(mContext);
                if (connected) {
                    if (status == 1 && count != 0) {
                        currentPage_up++;
                        getNetState();
                    } else {
                        Toast.makeText(mContext, "没有新的数据", Toast.LENGTH_SHORT).show();
                        lRecyclerView.refreshComplete(datas.size());
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                    }

                } else {

                    progressBar.setVisibility(View.GONE);
                    //提示网不给力，但是还是有视频页面
                    Toast.makeText(mContext, "网络不给力，请检查网络设置", Toast.LENGTH_SHORT).show();
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
//                //进入播放视频的页面
                VideoLists.ResultBean dataBean = datas.get(position);
//                Intent intent=new Intent(getActivity(),VideoDetailsActivity.class);
//                Intent intent=new Intent(getActivity(),AliVideoPlayer.class);
                Intent intent = new Intent(getActivity(), NoskinVpActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("dataBean", dataBean);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_open, R.anim.activity_backgroud);
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
        if (connected) {
            check_linear.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            RequestNetData();
        } else {


            //显示没有网的图片
            check_linear.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);


        }
    }

    private void RequestNetData() {

        if (isPulldownRefresh) { // 下拉刷新

            OkHttpUtils.get().url(Constants.PULLDOWNHEQUEST).build().execute(new MyCallBack());
        } else if (isRefresh) {
            //刷新

            OkHttpUtils.get().url(Constants.REFRESHEQUEST).build().execute(new MyCallBack());
        } else if (isLoadMore) {    // 上拉加载更多--默认
            OkHttpUtils.get().url(Constants.PULLUPEQUEST+ 89+"/pgnum/" + currentPage_up).build().execute(new MyCallBack());
        }
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
//            VideoList videoList = JSON.parseObject(response, VideoList.class);
//
            Log.i("TAG", response);
            VideoLists videoLists = JSON.parseObject(response, VideoLists.class);
            status = videoLists.getStauts();
            count = videoLists.getCount();
            if (status == 1  && count!=0) {
//
                data = videoLists.getResult();
//
                if (isLoadMore) {
                    //加载更多 不清楚数据

                    datas.addAll(data);
                } else if (isRefresh) {
                    datas.clear();
                    datas.addAll(0, data);

                } else if (isPulldownRefresh) {
//                    显示popwindows
//                    showPopwindows(count);
                    datas.clear();
                    datas.addAll(0, data);
                }

                if (null != datas && datas.size() > 0) {
                    SolveNetData();


                } else {
                    //提示没有数据
                    Toast.makeText(mContext, "没有新的数据", Toast.LENGTH_SHORT).show();
                    lRecyclerView.refreshComplete(datas.size());
                    progressBar.setVisibility(View.GONE);
                    mLRecyclerViewAdapter.notifyDataSetChanged();



                }

            } else {
                //请求出错----提示信息请重试
                Toast.makeText(mContext, "没有新的数据", Toast.LENGTH_SHORT).show();
                lRecyclerView.refreshComplete(datas.size());
                progressBar.setVisibility(View.GONE);
                mLRecyclerViewAdapter.notifyDataSetChanged();
            }


        }

        /**
         * 处理网络数据
         */
        private void SolveNetData() {

            if (madapter == null && mLRecyclerViewAdapter == null) {
                madapter = new MyRcAdapter(mContext, datas);
                mLRecyclerViewAdapter = new LRecyclerViewAdapter(madapter);

//根据需要选择使用GridItemDecoration还是SpacesItemDecoration
                GridItemDecoration divider = new GridItemDecoration.Builder(getActivity())
                        .setHorizontal(R.dimen.default_divider_height)
                        .setColorResource(R.color.gh_gray)
                        .build();
                lRecyclerView.addItemDecoration(divider);
                lRecyclerView.setAdapter(mLRecyclerViewAdapter);
                lRecyclerView.setPullRefreshEnabled(true);  //允许下拉刷新
                lRecyclerView.setLoadMoreEnabled(true);      //允许加载更多
                progressBar.setVisibility(View.GONE);
                GridLayoutManager manager = new GridLayoutManager(mContext, 2);
                //setLayoutManager
                final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
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


            } else {
                handler.sendEmptyMessageDelayed(FINISHREFRESH, 1000);
//                     lRecyclerView.refreshComplete(data.size());
//                     mLRecyclerViewAdapter.notifyDataSetChanged();
            }


            progressBar.setVisibility(View.GONE);


        }

    }

    private void showPopwindows(int count) {
        View inflate = View.inflate(getActivity(), R.layout.refresh_count, null);
        TextView tv = (TextView) inflate.findViewById(R.id.tv_refresf_count);
        popupWindow = new PopupWindow(getActivity());
        popupWindow.setContentView(inflate);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setText("更新了" + count + "条数据");
        popupWindow.showAsDropDown(getActivity().findViewById(R.id.tabs));
        handler.sendEmptyMessageDelayed(CACLEPOP, 1000);
    }


}
