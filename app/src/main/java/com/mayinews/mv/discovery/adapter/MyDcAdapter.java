package com.mayinews.mv.discovery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mayinews.mv.R;
import com.mayinews.mv.discovery.bean.HotAuthorItemBean;
import com.mayinews.mv.discovery.view.MyListView;
import com.mayinews.mv.discovery.view.ListViewUtils;
import com.mayinews.mv.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by gary on 2017/6/24 0024.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class MyDcAdapter extends RecyclerView.Adapter {

    private Context context;

    public MyDcAdapter(Context context) {
        this.context = context;
    }

    /*
        定义item类型
         */
    private final int HOT_AUTHOR = 0;//热门作者
    private final int UPDATE_AUTHOR = 1;//最新更新作者
    private final int OTHER_AUTHOR = 2;//热门作者

    private int currentyType = HOT_AUTHOR;//当前item的类型

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据不同的类型来创建不同的ViewHodler
        if (viewType == HOT_AUTHOR) {
            View view = LayoutInflater.from(context).inflate(R.layout.hot_author_item, parent, false);
            //创建HotViewHolder
            return new HotViewHolder(context, view); //后面数据的参数

        } else if (viewType == UPDATE_AUTHOR){
            View view = LayoutInflater.from(context).inflate(R.layout.update_author, parent, false);
            //创建UpdateViewHolder
            return new UpdateViewHolder(context, view); //后面数据的参数


        }else if(viewType == OTHER_AUTHOR){
            View view = LayoutInflater.from(context).inflate(R.layout.other_author_item, parent, false);
            //创建UpdateViewHolder
            return new OtherViewHolder(context, view); //后面数据的参数
        }
            return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HOT_AUTHOR) {
            HotViewHolder hv = (HotViewHolder) holder;
            hv.setData();
        }else if(getItemViewType(position)==UPDATE_AUTHOR){
            UpdateViewHolder uv= (UpdateViewHolder) holder;
            uv.setData();
            
        }else if(getItemViewType(position)==OTHER_AUTHOR){
            OtherViewHolder ov= (OtherViewHolder) holder;
            ov.setData();

        }
    }

    /*
        HotAuthor的ViewHolder
     */
    class HotViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private MyListView listView;

        public HotViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            listView = (MyListView) itemView.findViewById(R.id.hot_listView);
        }

        public void setData() {
            List<HotAuthorItemBean> data = new ArrayList<>();

              for(int i = 0; i < 30; i++) {
                  HotAuthorItemBean d = new HotAuthorItemBean("http://static.mayinews.com/Uploads/Picture/video_avatar.png", "作者" + i, "喜欢搞笑小段子");
                  data.add(d);
              }
//            OkHttpUtils.get().url(Constants.VIDEO_LIST).build().execute(new StringCallback() {
//                @Override
//                public void onError(Call call, Exception e, int id) {
//
//                }
//
//                @Override
//                public void onResponse(String response, int id) {
//
//                }
//            });
            HAListViewAdapter adapter = new HAListViewAdapter(context, data);
            listView.setAdapter(adapter);

        }
    }
    /*
    创建updateviewHolder
     */
    class UpdateViewHolder extends RecyclerView.ViewHolder{
        private Context context;
        private MyListView  listView;

        public UpdateViewHolder(Context context, View view) {
            super(view);
            this.context=context;
            listView= (MyListView ) view.findViewById(R.id.update_listView);
//            ListViewUtils.setListViewHeightBasedOnChildren(listView);
        }

        public void setData() {
            listView.setAdapter(new UpListViewAdapter(context));
        }
    }

    class OtherViewHolder extends RecyclerView.ViewHolder{
         private Context context;
        private MyListView listView;


        public OtherViewHolder(Context context, View view) {
            super(view);
            this.context=context;
            listView = (MyListView) itemView.findViewById(R.id.other_listView);
        }

        public void setData() {

            listView.setAdapter(new OtherAuthorAdapter(context));
        }
    }

    /**
     * 返回当前item的数量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        //一共有三种，开发时，没写完一种来改变。
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        //根据item的位置来设置item类型
        switch (position) {
            case HOT_AUTHOR:
                currentyType = HOT_AUTHOR;
                break;
            case UPDATE_AUTHOR:
                currentyType = UPDATE_AUTHOR;
                break;
            case OTHER_AUTHOR:
                currentyType = OTHER_AUTHOR;
                break;
        }
        return currentyType;
    }
}
