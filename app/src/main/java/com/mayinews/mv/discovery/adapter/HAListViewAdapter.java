package com.mayinews.mv.discovery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mayinews.mv.R;
import com.mayinews.mv.discovery.bean.HotAuthorItemBean;

import java.util.List;

/**
 * Created by gary on 2017/6/24 0024.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class HAListViewAdapter extends BaseAdapter {
    private Context context;
    private List<HotAuthorItemBean> datas;


    public HAListViewAdapter(Context context, List<HotAuthorItemBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HotAuthorItemBean bean = datas.get(position);
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hot_author, parent, false);
//            View view = View.inflate(context, R.layout.item_hot_author, null);
            viewHolder=new ViewHolder();
            viewHolder.authorIcon= (ImageView) convertView.findViewById(R.id.authorIcon);
            viewHolder.authorName= (TextView) convertView.findViewById(R.id.authorName);
            viewHolder.authorSign= (TextView) convertView.findViewById(R.id.authorSign);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.authorName.setText(bean.getAuthorName());
        viewHolder.authorSign.setText(bean.getAuthorSign());

        return convertView;
    }

    class ViewHolder {
           ImageView authorIcon;
           TextView authorName;
            TextView authorSign;

    }
}
