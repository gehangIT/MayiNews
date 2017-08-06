package com.mayinews.mv.home.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by gary on 2017/6/14 0014.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class MyVpAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<String> titles;
    private List<Fragment> mfragments;
    public MyVpAdapter(FragmentManager fm, Context context,List<String> titles,List<Fragment> mfragments ) {
        super(fm);
        this.context=context;
        this.mfragments=mfragments;
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mfragments.get(position);
    }

    @Override
    public int getCount() {
        return mfragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
