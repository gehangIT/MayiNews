package com.mayinews.mv.attention.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonHeader;
import com.mayinews.mv.R;
import com.mayinews.mv.attention.bean.AuthorsDateBean;
import com.mayinews.mv.base.BaseFragment;
import com.mayinews.mv.discovery.activity.PublishActivity;
import com.mayinews.mv.discovery.adapter.MyDcAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by gary on 2017/6/14 0014.
 * 微信:GH-12-10
 * QQ:1683701403
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class AttentionFragment extends BaseFragment {


    private LRecyclerView mRecyclerView;
    private TextView publish;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.discovery, null);
        mRecyclerView= (LRecyclerView) view.findViewById(R.id.discovery_lr);
        publish= (TextView) view.findViewById(R.id.publish);
        setListener();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
//        View header = LayoutInflater.from(mContext).inflate(R.layout.discovery_head,(ViewGroup)view.findViewById(android.R.id.content), false);


        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setNestedScrollingEnabled(false);  //不允许嵌套的滑动


        return view;
    }

    private void setListener() {
        //发布的监听
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),PublishActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void initData() {
        //请求数据

        MyDcAdapter myDcAdapter = new MyDcAdapter(mContext);
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(myDcAdapter);
        CommonHeader headerView = new CommonHeader(getActivity(), R.layout.discovery_head);
        lRecyclerViewAdapter.addHeaderView(headerView);
        mRecyclerView.setAdapter(lRecyclerViewAdapter);

    }
}
