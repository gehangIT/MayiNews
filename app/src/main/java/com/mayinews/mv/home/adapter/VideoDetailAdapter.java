package com.mayinews.mv.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aliyun.common.project.Text;
import com.mayinews.mv.R;
import com.mayinews.mv.discovery.view.MyListView;

/**
 * Created by gary on 2017/6/26 0026.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class VideoDetailAdapter extends RecyclerView.Adapter {

    private Context context;
    private LayoutInflater inflater;
     /*
         一下定义Adapter的类型
      */

    public VideoDetailAdapter(Context context) {
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    private static final int VIDEO_INFO=0;  //视频的相关信息
    private static final int  VIDEO_AUTHOR=1;  //视频的作者
    private static final int  GUESS_LIKE=2;  //猜你喜欢
    private static final int  SPLENDID_COMMENT=3;  //精彩的评论

    private int currentType;   //当前的item类型
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==VIDEO_INFO){
            //创建视频信息的ViewHolder
            View view = inflater.inflate(R.layout.video_detail_info, parent, false);
            return  new VideoInfoViewHolder(context,view);
        }else if(viewType==VIDEO_AUTHOR){
            View view = inflater.inflate(R.layout.video_author_info, parent, false);
            return  new VideoAuthorInfoViewHolder(context,view);
        }else if(viewType==GUESS_LIKE){

            View view = inflater.inflate(R.layout.guess_author_item, parent, false);
            return  new GuessAuthorViewHolder(context,view);
        }else if(viewType==SPLENDID_COMMENT){
            View view = inflater.inflate(R.layout.pinglun_author_item, parent, false);
            return  new PinglunViewHolder(context,view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
           if(getItemViewType(position)==VIDEO_INFO){
                      VideoInfoViewHolder vv= (VideoInfoViewHolder) holder;
                      vv.setData();

           }else if(getItemViewType(position)==VIDEO_AUTHOR){

               VideoAuthorInfoViewHolder av= (VideoAuthorInfoViewHolder) holder;
               av.setData();
           }else  if(getItemViewType(position)==GUESS_LIKE){

               GuessAuthorViewHolder gv= (GuessAuthorViewHolder) holder;
               gv.setData();
           }else if(getItemViewType(position)==SPLENDID_COMMENT){

               PinglunViewHolder pv= (PinglunViewHolder) holder;
               pv.setData();
           }
    }



    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case VIDEO_INFO:
                currentType=VIDEO_INFO;
                break;
            case VIDEO_AUTHOR:
                currentType=VIDEO_AUTHOR;
                break;
            case GUESS_LIKE:
                currentType=GUESS_LIKE;
                break;
            case SPLENDID_COMMENT:
                currentType=SPLENDID_COMMENT;
                break;

        }
        return currentType;
    }


    class VideoInfoViewHolder extends  RecyclerView.ViewHolder{
        private Context context;
        TextView videoInfo;
        TextView playTime;
        TextView playTimes;
        TextView videoCollection;
        TextView videoShared;
        TextView videoDownload;

        public VideoInfoViewHolder(Context context,View itemView) {
            super(itemView);
            this.context=context;
            videoInfo= (TextView) itemView.findViewById(R.id.videoTitle);
            playTime= (TextView) itemView.findViewById(R.id.play_time);
            playTimes= (TextView) itemView.findViewById(R.id.play_times);
            videoCollection= (TextView) itemView.findViewById(R.id.collection);
            videoShared= (TextView) itemView.findViewById(R.id.shared);
            videoDownload= (TextView) itemView.findViewById(R.id.download);

        }
        //绑定数据
        public void setData() {
        }
    }

    class VideoAuthorInfoViewHolder extends RecyclerView.ViewHolder{
            private Context context;
            ImageView authorIcon;
            TextView authorName;
            TextView attenAuthor;
        public VideoAuthorInfoViewHolder(final Context context, View itemView) {
            super(itemView);
            this.context=context;
            authorIcon= (ImageView) itemView.findViewById(R.id.authorIcon);
            authorName= (TextView) itemView.findViewById(R.id.authorName);
            attenAuthor= (TextView) itemView.findViewById(R.id.atten);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"itemView",Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setData() {
        }
    }
     class GuessAuthorViewHolder extends RecyclerView.ViewHolder{
              private Context context;
              private MyListView listview;

         public GuessAuthorViewHolder(Context context,View itemView) {
             super(itemView);
             this.context=context;
             listview= (MyListView) itemView.findViewById(R.id.guess_listView);
             GuessAuthorAdapter adapter = new GuessAuthorAdapter(context);
             listview.setAdapter(adapter);
         }


         public void setData() {

         }
     }


     class PinglunViewHolder extends RecyclerView.ViewHolder{

               private Context context;
         private MyListView listView;
         public PinglunViewHolder(Context context,View itemView) {
             super(itemView);
             this.context=context;
             listView= (MyListView) itemView.findViewById(R.id.pinglun_listView);

             listView.setAdapter(new PinglunAdapter(context));
         }

         public void setData() {

         }

     }

}
