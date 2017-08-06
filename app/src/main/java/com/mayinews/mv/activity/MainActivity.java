package com.mayinews.mv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mayinews.mv.R;
import com.mayinews.mv.attention.fragment.AttentionFragment;
import com.mayinews.mv.base.BaseFragment;
import com.mayinews.mv.discovery.fragment.DiscoveryFragment;
import com.mayinews.mv.home.fragment.HomeFragment;
import com.mayinews.mv.user.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
//import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.framelayout)
    FrameLayout fragment;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_nice)
//    RadioButton rbNice;
//    @BindView(R.id.rb_discover)
    RadioButton rbDiscover;
    @BindView(R.id.rb_mine)
    RadioButton rbMine;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    private onProBackListener listener;
    public interface  onProBackListener{
        void onBack();
    }

    public void setOnBackListener(onProBackListener listener){
        this.listener=listener;

    }
    private List<BaseFragment> mfragments;
    private int position;
    /**
     * 上次切换的Fragment
     */
    private  Fragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
        setListener();
        rgMain.check(R.id.rb_home);
    }

    private void initFragment() {
        mfragments = new ArrayList<>();
        mfragments.add(new HomeFragment());
//        mfragments.add(new AttentionFragment());
        mfragments.add(new DiscoveryFragment());
        mfragments.add(new UserFragment());
    }

    private void setListener() {

        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;

                        break;
                    case R.id.rb_nice:
                        position = 1;
                        break;
//                    case R.id.rb_discover:
//                        position = 2;
//                        break;
                    case R.id.rb_mine:
                        position = 2;
                        break;
                    default:
                        position = 0;
                        break;
                }

                BaseFragment fragment = getFragment(position);
                switchFrament(mContent, fragment);
            }
        });

    }

    private BaseFragment getFragment(int position) {

        return mfragments.get(position);

    }


    /**
     * @param from 刚显示的Fragment,马上就要被隐藏了
     * @param to   马上要切换到的Fragment，一会要显示
     */
    private void switchFrament(Fragment from, Fragment to) {
        if (from != to) {
            mContent = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //才切换
            //判断有没有被添加
            if (!to.isAdded()) {
                //to没有被添加
                //from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                //添加to
                if (to != null) {
                    ft.add(R.id.framelayout, to).commit();
                }
            } else {
                //to已经被添加
                // from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                //显示to
                if (to != null) {
                    ft.show(to).commit();
                }
            }
        }

    }


}