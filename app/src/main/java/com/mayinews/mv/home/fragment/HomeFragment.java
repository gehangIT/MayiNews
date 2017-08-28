package com.mayinews.mv.home.fragment;

import android.app.WallpaperInfo;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gxz.PagerSlidingTabStrip;
import com.mayinews.mv.MyApplication;
import com.mayinews.mv.R;
import com.mayinews.mv.base.BaseFragment;
import com.mayinews.mv.home.activity.RecordActivity;
import com.mayinews.mv.home.adapter.MyVpAdapter;
import com.mayinews.mv.home.bean.VideoTag;
import com.mayinews.mv.utils.DensityUtil;
import com.mayinews.mv.utils.SPUtils;
import com.mayinews.mv.utils.UnicodeTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gary on 2017/6/14 0014.
 * 微信:GH-12-10
 * QQ:1683701403
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class HomeFragment extends BaseFragment  {

    private List<Fragment> mFragments;
    private ViewPager viewPager;
    private PagerSlidingTabStrip tabs;

   List<String> titles;
    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.homefragment, null);
        tabs= (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        viewPager= (ViewPager) view.findViewById(R.id.viewpager);


        return view;
    }



    @Override
    protected void initData() {
        mFragments = new ArrayList<>();
        NewestFragment newestFragment = new NewestFragment();
        SiftFragment siftFragment = new SiftFragment();
        mFragments.add(newestFragment);
         mFragments.add(siftFragment);
        mFragments.add(new CuteBabyFragment());
        mFragments.add(new FunnyFragment());
        mFragments.add(new LocaleFragment());
        mFragments.add(new VarietyFragment());
        mFragments.add(new WelfareFragment());
        mFragments.add(new LifeFragment());
        mFragments.add(new MovieFragment());
        mFragments.add(new FoodFragment());
        mFragments.add(new MusicFragment());
        mFragments.add(new GameFragment());
        mFragments.add(new ACGFragment());
        mFragments.add(new DanceFragment());
        mFragments.add(new YagamiFragment());

        titles=new ArrayList<>();
        titles.add("热门");
        titles.add("推荐");
        titles.add("萌娃萌宠");
        titles.add("搞笑");
        titles.add("现场");
        titles.add("明星综艺");
        titles.add("福利");
        titles.add("生活");
        titles.add("影视");
        titles.add("美食");
        titles.add("音乐");
        titles.add("游戏");
        titles.add("二次元");
        titles.add("舞蹈");
        titles.add("涨姿势");

        viewPager.setOffscreenPageLimit(15);
        MyVpAdapter adapter = new MyVpAdapter(getActivity().getSupportFragmentManager(), mContext,titles, mFragments);
        viewPager.setAdapter(adapter);
        tabs.setViewPager(viewPager);


    }








}
