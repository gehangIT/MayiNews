package com.mayinews.mv.user.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.mayinews.mv.R;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class MyGridViewAdapter extends BaseAdapter {
    private Context context;
    private int[] images;//数据源
    private int mIndex; // 页数下标，标示第几页，从0开始
    private int mPargerSize;// 每页显示的最大的数量



    //定义接口
    public interface OnchooseListener{

        void  onChoose(int position);

    }

    //
    OnchooseListener listener;

    public void setOnChooseListener(OnchooseListener listener){

        this.listener=listener;

    }
    public MyGridViewAdapter(Context context,int[] images ,
                            int mIndex, int mPargerSize) {
        this.context = context;
        this.images = images;
        this.mIndex = mIndex;
        this.mPargerSize = mPargerSize;
    }

    /**
     * 先判断数据及的大小是否显示满本页lists.size() > (mIndex + 1)*mPagerSize
     * 如果满足，则此页就显示最大数量lists的个数
     * 如果不够显示每页的最大数量，那么剩下几个就显示几个
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return images.length > (mIndex + 1) * mPargerSize ?
                mPargerSize : (images.length - mIndex*mPargerSize);
    }

    @Override
    public Object getItem(int position) {
        return images[position + mIndex * mPargerSize];
    }



    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0 + mIndex * mPargerSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_gridview, null);

            holder.imageView = (ImageView)convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        //重新确定position因为拿到的总是数据源，数据源是分页加载到每页的GridView上的
        final int pos = position + mIndex * mPargerSize;//假设mPageSiez
        //假设mPagerSize=8，假如点击的是第二页（即mIndex=1）上的第二个位置item(position=1),那么这个item的实际位置就是pos=9
        holder.imageView.setImageResource(images[pos]);

        if(listener!=null) {
            // 添加item监听
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
//				Toast.makeText(context, "您点击了"  + pos, Toast.LENGTH_SHORT).show();
                    listener.onChoose(pos);
                }
            });
        }
        return convertView;
    }
    static class ViewHolder{

        private ImageView imageView;
    }
}

