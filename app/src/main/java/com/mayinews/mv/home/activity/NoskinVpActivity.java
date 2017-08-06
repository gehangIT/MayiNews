package com.mayinews.mv.home.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.aliyun.vodplayer.downloader.AliyunDownloadManager;
import com.aliyun.vodplayer.downloader.AliyunDownloadMediaInfo;
import com.aliyun.vodplayer.media.AliyunDataSource;
import com.aliyun.vodplayer.media.AliyunPlayAuth;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.aliyun.vodplayerview.utils.ScreenUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.mayinews.mv.R;
import com.mayinews.mv.home.adapter.VideoDetailAdapter;
import com.mayinews.mv.home.bean.ItemVideoBean;
import com.mayinews.mv.home.bean.VideoList;
import com.mayinews.mv.utils.Constants;
import com.mayinews.mv.home.utils.Formatter;
import com.mayinews.mv.utils.DialogUtils;
import com.mayinews.mv.utils.NetUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

public class NoskinVpActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView surfaceBackgroud;
    private LinearLayout check_linear;
    private LRecyclerView lrecyclerView;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private static final int TIME = 0;
    private static final int HIDDLECONTROL = 1;
    private SurfaceView surfaceView;
    private AliyunVodPlayer aliyunVodPlayer;
    private VideoList.DataBean datas; //传递过来的item视频bean
    private String playAuth;//播放凭证，有失效时间
    private String videoId;//视频的vid
    private RelativeLayout rl;//包含sufface的容器
    private ImageView iv_playState; //播放暂停
    private ProgressBar progressBar1;
    private RelativeLayout progressBar2;//下面的progressBar,是放在了一个RelativeLayouth中。
    private View media_controll;
    private View play_finish;
    private ImageView reStart;

    private ImageView title_back;
    private TextView title;
    private Boolean isHiddleControl = false;//是否隐藏控制栏


    private SeekBar sb_progressBar;   //进度条
    private TextView tv_currentDuration;    //显示当前所在时长
    private TextView tv_duration;          //显示视频的总时长
    private ImageView iv_changeScreen;     //改变全屏的imageview

    private Boolean isFullScreen = false;//标记是否全屏
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME:
                    showVideoProgressInfo();
                    break;
                case HIDDLECONTROL:
                    hiddleControl();
                    break;
            }
        }
    };


    private boolean isPlayFinished = false;  //标记视频是否播放完成
    private boolean isReplay=false;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noskin_vp);

        //得到序列换的
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        datas = bundle.getParcelable("dataBean");

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceBackgroud= (ImageView) findViewById(R.id.surfaceBackgroud);
        media_controll = findViewById(R.id.media_controll);
        lrecyclerView = (LRecyclerView) findViewById(R.id.lrecyclerView);
        check_linear = (LinearLayout) findViewById(R.id.check_linear);
        title_back = (ImageView) media_controll.findViewById(R.id.title_back);
        iv_playState = (ImageView) media_controll.findViewById(R.id.iv_playState);
        title = (TextView) findViewById(R.id.title);
        sb_progressBar = (SeekBar) media_controll.findViewById(R.id.seekbar);
        tv_currentDuration = (TextView) media_controll.findViewById(R.id.currentDuration);
        tv_duration = (TextView) media_controll.findViewById(R.id.Duration);
        iv_changeScreen = (ImageView) media_controll.findViewById(R.id.changeScreen);
        play_finish = findViewById(R.id.play_finish);
        reStart = (ImageView) play_finish.findViewById(R.id.reStart);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar2 = (RelativeLayout) findViewById(R.id.progressBar2);
        rl = (RelativeLayout) findViewById(R.id.rl);
        //控制栏的按钮点击操作
        title_back.setOnClickListener(this);
        iv_playState.setOnClickListener(this);
        iv_changeScreen.setOnClickListener(this);
        reStart.setOnClickListener(this);
        sb_progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    aliyunVodPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //移除隐藏控制栏的消息
                handler.removeMessages(HIDDLECONTROL);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //发送隐藏控制栏的消息
                handler.sendEmptyMessageDelayed(HIDDLECONTROL, 4000);
            }
        });


        check_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNetState();
            }
        });
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override

            public void surfaceCreated(SurfaceHolder holder) {
                aliyunVodPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                aliyunVodPlayer.surfaceChanged();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                aliyunVodPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                aliyunVodPlayer.surfaceChanged();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlayFinished) {
                    if (!isHiddleControl) {
                        hiddleControl();
                    } else {
                        showControl();

                    }
                }

            }

            private void showControl() {
//                linear_bottom.setVisibility(View.VISIBLE);
//                linear_top.setVisibility(View.VISIBLE);
                media_controll.setVisibility(View.VISIBLE);
                //过四秒自动隐藏控制栏
                handler.sendEmptyMessageDelayed(HIDDLECONTROL, 4000);
                isHiddleControl = false;
            }
        });
        initVodPlayer();
        /**
         * 根据网络状态去请求数据
         */

        getNetState();

    }

    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {

            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://mv.mayinews.com").build());
        }
    }
    private void initVodPlayer() {
        aliyunVodPlayer = new AliyunVodPlayer(this);
        aliyunVodPlayer.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                Toast.makeText(NoskinVpActivity.this, "准备成功", Toast.LENGTH_SHORT).show();
                //准备成功之后可以调用start方法开始播放
                isPlayFinished = false;
                play_finish.setVisibility(View.GONE);
                progressBar1.setVisibility(View.GONE);
                hiddleControl();
                showVideoProgressInfo();

                aliyunVodPlayer.start();
            }
        });
        aliyunVodPlayer.setOnErrorListener(new IAliyunVodPlayer.OnErrorListener() {
            @Override
            public void onError(int arg0, int arg1, String msg) {
                Toast.makeText(NoskinVpActivity.this, "失败！！！！原因：" + msg, Toast.LENGTH_SHORT).show();
            }
        });
        aliyunVodPlayer.setOnCompletionListener(new IAliyunVodPlayer.OnCompletionListener() {
            @Override
            public void onCompletion() {

                Toast.makeText(NoskinVpActivity.this, "播放结束：", Toast.LENGTH_SHORT).show();
                stopUpdateTimer();
                aliyunVodPlayer = null;

                media_controll.setVisibility(View.GONE);
                play_finish.setVisibility(View.VISIBLE);
                media_controll.setVisibility(View.GONE);
            }
        });
        //缓冲进度
        aliyunVodPlayer.setOnBufferingUpdateListener(new IAliyunVodPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(int percent) {
                updateBufferingProgress(percent);
            }
        });
        aliyunVodPlayer.setOnChangeQualityListener(new IAliyunVodPlayer.OnChangeQualityListener() {
            @Override
            public void onChangeQualitySuccess(String finalQuality) {
                Log.d("TAG", "切换清晰度成功");
            }

            @Override
            public void onChangeQualityFail(int code, String msg) {
                Log.d("TAG", "切换清晰度失败。。。" + code + " ,  " + msg);
            }
        });

        //滑动seekbar的缓冲的监听
        aliyunVodPlayer.setOnLoadingListener(new IAliyunVodPlayer.OnLoadingListener() {
            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadEnd() {

            }

            @Override
            public void onLoadProgress(int i) {
                Log.i("TAG", "PROGRESS=" + i);
                sb_progressBar.setSecondaryProgress(i);
            }
        });
        aliyunVodPlayer.enableNativeLog();
        //设置播放的surface
//        aliyunVodPlayer.setDisplay(surfaceView.getHolder());

    }




    private void playVideo() {
        initVodPlayer();
        OkHttpUtils.get().url(Constants.GETPLAYAUTH + datas.getVid()).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("TAG", response);
                ItemVideoBean bean = JSON.parseObject(response, ItemVideoBean.class);
                ItemVideoBean.DataBean data = bean.getData();
                playAuth = data.getPlayAuth();
                ItemVideoBean.DataBean.VideoMetaBean videoMeta = data.getVideoMeta();
                videoId = videoMeta.getVideoId();
                Log.i("TAG", "playAuth=" + playAuth + "    videoId=" + videoId);


                /**
                 * 根据播放凭证方式播放
                 */

                AliyunPlayAuth.AliyunPlayAuthBuilder aliyunAuthInfoBuilder = new AliyunPlayAuth.AliyunPlayAuthBuilder();

                aliyunAuthInfoBuilder.setPlayAuth(playAuth);
                aliyunAuthInfoBuilder.setVid(videoId);
                aliyunAuthInfoBuilder.setQuality(IAliyunVodPlayer.QualityValue.QUALITY_ORIGINAL);
                aliyunVodPlayer.setAuthInfo(aliyunAuthInfoBuilder.build());
                //设置surface的回调。此为必须的操作
               if(isReplay){
                   if (surfaceView != null) {
                       //刷新surfaceHolder
                       surfaceView.setVisibility(View.GONE);
                       surfaceView.setVisibility(View.VISIBLE);
                   }
               }
                aliyunVodPlayer.prepareAsync();
            }
        });
    }

    private void hiddleControl() {
//        linear_bottom.setVisibility(View.INVISIBLE);
//        linear_top.setVisibility(View.INVISIBLE);

        handler.removeMessages(HIDDLECONTROL);
        media_controll.setVisibility(View.GONE);
        isHiddleControl = true;
    }

    private void outFullScreen() {
        isFullScreen = false;
        //转为竖屏了。
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //显示状态栏
        NoskinVpActivity.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        rl.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        //设置view的布局，宽高之类
        ViewGroup.LayoutParams surfaceViewLayoutParams = rl.getLayoutParams();
        surfaceViewLayoutParams.height = (int) (ScreenUtils.getWight(NoskinVpActivity.this) * 9.0f / 16);
        surfaceViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                          setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    private void IntoFullScreen() {
        isFullScreen = true;
        //设置为全屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //隐藏状态栏
        NoskinVpActivity.this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        rl.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        //设置view的布局，宽高
        ViewGroup.LayoutParams surfaceViewLayoutParams = rl.getLayoutParams();
        surfaceViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        surfaceViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                          setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        title.setSingleLine(false);
        title.setMaxEms(30);
    }

    private void stopUpdateTimer() {

        handler.removeMessages(TIME);
    }

    private void startUpdateTimer() {
        handler.removeMessages(TIME);
        handler.sendEmptyMessageDelayed(TIME, 1000);
    }

    private void showVideoProgressInfo() {
        int curPosition = (int) aliyunVodPlayer.getCurrentPosition();
        tv_currentDuration.setText(Formatter.formatTime(curPosition));
        int duration = (int) aliyunVodPlayer.getDuration();
        tv_duration.setText(Formatter.formatTime(duration));

        sb_progressBar.setMax(duration);
        sb_progressBar.setProgress(curPosition);
        title.setText(datas.getTitle());
        startUpdateTimer();
    }

    private void updateBufferingProgress(int percent) {
        int duration = (int) aliyunVodPlayer.getDuration();
        sb_progressBar.setSecondaryProgress((int) (duration * percent * 1.0f / 100));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //保存播放器的状态，供resume恢复使用。
        resumePlayerState();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //onStop中记录下来的状态，在这里恢复使用
        savePlayerState();

    }


    //用来记录前后台切换时的状态，以供恢复。
    private IAliyunVodPlayer.PlayerState mPlayerState;

    private void resumePlayerState() {
        if (mPlayerState == IAliyunVodPlayer.PlayerState.Paused) {
            aliyunVodPlayer.pause();
        } else if (mPlayerState == IAliyunVodPlayer.PlayerState.Started) {
            aliyunVodPlayer.start();
        }
    }

    private void savePlayerState() {
        mPlayerState = aliyunVodPlayer.getPlayerState();
        if (aliyunVodPlayer.isPlaying()) {
            //然后再暂停播放器
            aliyunVodPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aliyunVodPlayer.release();
        stopUpdateTimer();
//        handler = null;
    }

    private void initData() {


          //下面的网络请求。
        Glide.with(this)
                .load("http://p.qpic.cn/videoyun/0/2449_bfbbfa3cea8f11e5aac3db03cda99974_1/640");

        lrecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        VideoDetailAdapter adapter = new VideoDetailAdapter(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.default_divider_height)

                .setColorResource(R.color.lightgray)
                .build();
        lrecyclerView.addItemDecoration(divider);
        progressBar2.setVisibility(View.GONE);
        lrecyclerView.setAdapter(mLRecyclerViewAdapter);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.title_back:
                if (!isFullScreen) {
                    finish();
                } else {

                    //退出全屏
                    outFullScreen();


                }
                break;
            case R.id.iv_playState:
                if (aliyunVodPlayer.getPlayerState() == IAliyunVodPlayer.PlayerState.Started) {
                    iv_playState.setImageResource(R.drawable.pubic_icon_play_88px);
                    aliyunVodPlayer.pause();
                } else {
                    iv_playState.setImageResource(R.drawable.pubic_icon_suspend);
                    aliyunVodPlayer.start();
                }
                break;
            case R.id.changeScreen:
                if (!isFullScreen) {
                    IntoFullScreen();
                } else {
                    outFullScreen();

                }
                handler.removeMessages(HIDDLECONTROL);
                handler.sendEmptyMessageDelayed(HIDDLECONTROL, 4000);
                break;
            case R.id.reStart:
                play_finish.setVisibility(View.GONE);

                /**
                 * 根据网络状态去请求数据
                 */
//                getNetState();
                AliyunVodPlayer tmpVodPlayer = aliyunVodPlayer;
                if(tmpVodPlayer != null){
                    tmpVodPlayer.release();
                }
                isReplay=true;
                aliyunVodPlayer = null;
                playVideo();


                handler.removeMessages(HIDDLECONTROL);
                handler.sendEmptyMessageDelayed(HIDDLECONTROL, 4000);
                break;


        }

    }


    private void getNetState() {

        boolean connected = NetUtils.isConnected(this);
        if (connected) {
            hiddleControl();
            progressBar1.setVisibility(View.VISIBLE);
            check_linear.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);
            initData();
            if (NetUtils.isWifi(this)) {
               playVideo();
            } else {
                progressBar1.setVisibility(View.GONE);
                progressBar2.setVisibility(View.GONE);
                //弹出对话框，提示是否使用流量播放
                DialogUtils.alert(this, R.mipmap.ic_launcher, "提示", "当前使用的是移动网络,确定继续播放吗?", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        playVideo();
                    }
                }, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
            }
        } else {


            Toast.makeText(this, "网络不给力", Toast.LENGTH_SHORT).show();
            //显示没有网的图片
            check_linear.setVisibility(View.VISIBLE);
            rl.setVisibility(View.GONE);


        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
      if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){

          if(isFullScreen){

              outFullScreen();
              isFullScreen=false;
          }

      }
        return super.onKeyUp(keyCode, event);
    }



}