package com.mayinews.mv.attention.fragment;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

import com.mayinews.mv.R;
import com.mayinews.mv.attention.bean.AuthorsDateBean;
import com.mayinews.mv.base.BaseFragment;

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



    private ImageView iv_attention;
    private TextView tv_rmText;
    private ListView lv_authors;
    private ListView listView;
    private List<AuthorsDateBean> datas;
    private Myadapter adapter;
    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.attention_fragment, null);
        iv_attention= (ImageView) view.findViewById(R.id.iv_attention);
        tv_rmText= (TextView) view.findViewById(R.id.tv_rmText);
        lv_authors= (ListView) view.findViewById(R.id.listView);
        listView= (ListView) view.findViewById(R.id.listView);
        View headview = View.inflate(mContext, R.layout.listview_headview, null);
        listView.addHeaderView(headview);

        setListener();
        return view;

    }

    private void setListener() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext,"item"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void initData() {
        //请求数据
        datas=new ArrayList<>();
        for(int i=0;i<20;i++){
            AuthorsDateBean bean = new AuthorsDateBean("", "作者" + i);
            datas.add(bean);
        }


        //设置适配器
        adapter=new Myadapter();
        listView.setAdapter(adapter);
    }


    class Myadapter extends BaseAdapter{

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
            ViewHolder viewHolder;
            AuthorsDateBean dateBean = datas.get(position);
            if(convertView==null) {
                    convertView=LayoutInflater.from(mContext).inflate(R.layout.author_item,parent,false);
                    viewHolder=new ViewHolder();
                    viewHolder.authorIcon= (ImageView) convertView.findViewById(R.id.authorIcon);
                    viewHolder.authorName= (TextView) convertView.findViewById(R.id.authorName);
                viewHolder.authorAtten= (TextView) convertView.findViewById(R.id.authorAtten);
                convertView.setTag(viewHolder);
                }else{
                    viewHolder= (ViewHolder) convertView.getTag();
                }
                viewHolder.authorName.setText(dateBean.getAuthorName());
            viewHolder.authorAtten.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"关注",Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }
        class ViewHolder {
            ImageView authorIcon;
            TextView  authorName;
            TextView  authorAtten;


        }
    }
}
