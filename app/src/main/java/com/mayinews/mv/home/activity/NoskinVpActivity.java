package com.mayinews.mv.home.activity;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.mayinews.mv.JNI;
import com.mayinews.mv.MyApplication;
import com.mayinews.mv.R;
import com.mayinews.mv.home.adapter.VideoDetailAdapter;
import com.mayinews.mv.home.bean.CommentLists;
import com.mayinews.mv.home.bean.ItemVideoBean;
import com.mayinews.mv.home.bean.VideoList;
import com.mayinews.mv.home.bean.VideoLists;
import com.mayinews.mv.user.activity.LoginActivity;
import com.mayinews.mv.utils.Constants;
import com.mayinews.mv.home.utils.Formatter;
import com.mayinews.mv.utils.DialogUtils;
import com.mayinews.mv.utils.NetUtils;
import com.mayinews.mv.utils.SPUtils;
import com.mayinews.mv.utils.StringUtil;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

import static java.security.AccessController.getContext;

public class NoskinVpActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView surfaceBackgroud;
    private LinearLayout check_linear;
    private LRecyclerView lrecyclerView;
    private LinearLayout ll_bottom;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private static final int TIME = 0;
    private static final int HIDDLECONTROL = 1;
    private SurfaceView surfaceView;
    private boolean isPrepared=false;//标记是否视频准备好了
    private AliyunVodPlayer aliyunVodPlayer;
    private VideoLists.ResultBean datas; //传递过来的item视频bean
    private String playAuth;//播放凭证，有失效时间
    private String videoId;//视频的vid
    private RelativeLayout rl;//包含sufface的容器
    private ImageView iv_playState; //播放暂停
    private ProgressBar progressBar1;
    private RelativeLayout progressBar2;//下面的progressBar,是放在了一个RelativeLayouth中。
    private View media_controll;
    private View play_finish;
    private ImageView reStart;
    private ImageView to_comments;

    private ImageView title_back;
    private TextView title;
    private Boolean isHiddleControl = false;//是否隐藏控制栏

    private LinearLayout bottom;
    private SeekBar sb_progressBar;   //进度条
    private TextView tv_currentDuration;    //显示当前所在时长
    private TextView tv_duration;          //显示视频的总时长
    private ImageView iv_changeScreen;     //改变全屏的imageview
    private ImageView iv_video;
    private ImageView iv_bottom_back;    //底部返回按钮
    private Button btn_open_comment;     //打开

    private EditText et_com;//评论pop中的输入框


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
    private boolean isReplay = false;
    private List<VideoLists.ResultBean> guessLikeVideos;  //猜你喜欢推荐的相关的视频
    private List<CommentLists.ResultBean> result;
    private RecyclerView.LayoutManager layoutManager
            ;
    private String token;
    private String uid;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noskin_vp);

        //得到序列换的

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        datas = bundle.getParcelable("dataBean");

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceBackgroud = (ImageView) findViewById(R.id.surfaceBackgroud);
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
        bottom = (LinearLayout) findViewById(R.id.bottom);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        iv_bottom_back = (ImageView) findViewById(R.id.iv_bottom_back);
        reStart = (ImageView) play_finish.findViewById(R.id.reStart);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar2 = (RelativeLayout) findViewById(R.id.progressBar2);
        iv_video = (ImageView) findViewById(R.id.iv_video);
        btn_open_comment = (Button) findViewById(R.id.btn_open_comment);
        rl = (RelativeLayout) findViewById(R.id.rl);
        to_comments = (ImageView)findViewById(R.id.to_comments);
        //控制栏的按钮点击操作

        //得到token
        JNI jni = new JNI();
        String key = jni.getString();
          token  = (String) SPUtils.get(NoskinVpActivity.this, key, "");

         uid = (String) SPUtils.get(NoskinVpActivity.this,MyApplication.USERUID,"");
        to_comments.setOnClickListener(this);
        title_back.setOnClickListener(this);
        iv_playState.setOnClickListener(this);
        iv_changeScreen.setOnClickListener(this);
        iv_bottom_back.setOnClickListener(this);
        btn_open_comment.setOnClickListener(this);
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
                        if(isPrepared){
                            showControl();
                        }


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
                //准备成功之后可以调用start方法开始播放
                isPlayFinished = false;
                isPrepared=true;
                play_finish.setVisibility(View.GONE);
                progressBar1.setVisibility(View.GONE);
                hiddleControl();
                showVideoProgressInfo();

                aliyunVodPlayer.start();
                if (aliyunVodPlayer.isPlaying()) {
                    iv_video.setVisibility(View.GONE);
                }

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
                if (isReplay) {
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
        ll_bottom.setVisibility(View.VISIBLE);  //显示底部
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
        ll_bottom.setVisibility(View.GONE);  //隐藏底部
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
        OkHttpUtils.getInstance().cancelTag(this);
//        handler = null;
    }

    private void initData() {

        progressBar2.setVisibility(View.VISIBLE);
        Glide.with(this).load(buildGlideUrl(datas.getCover())).into(iv_video);
        //下面的网络请求。
        OkHttpUtils.get().url(Constants.PULLDOWNHEQUEST).addParams("qid", "0")
                .addParams("tag", datas.getCid()).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                progressBar2.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(String response, int id) {
                //请求相关视频成功
                VideoLists videoLists = JSON.parseObject(response, VideoLists.class);
                Log.i("tag", "guessLikeVideos" + guessLikeVideos);
                guessLikeVideos = videoLists.getResult();

                //执行评论的接口请求
                JNI jni = new JNI();
                String key = jni.getString();
                String author = (String) SPUtils.get(NoskinVpActivity.this, key, "");
                OkHttpUtils.get().url(Constants.GETCOMMENT + datas.getVid() + ".html").addHeader("Authorization", "Bearer " + author).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        progressBar2.setVisibility(View.GONE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("tag",response);
                        CommentLists Comments = JSON.parseObject(response, CommentLists.class);
                        int count = Comments.getCount();

                         result = Comments.getResult();
                            //设置适配器

                            layoutManager= new LinearLayoutManager(NoskinVpActivity.this, LinearLayoutManager.VERTICAL, false);
                            lrecyclerView.setLayoutManager(layoutManager);

                            lrecyclerView.setPullRefreshEnabled(false);
                            VideoDetailAdapter adapter = new VideoDetailAdapter(NoskinVpActivity.this, datas, guessLikeVideos, result);
                            mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
                            lrecyclerView.setAdapter(mLRecyclerViewAdapter);
                            progressBar2.setVisibility(View.GONE);


                        bottom.setVisibility(View.VISIBLE);

                    }
                });
                ;

            }
        });


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
                if (tmpVodPlayer != null) {
                    tmpVodPlayer.release();
                }
                isReplay = true;
                aliyunVodPlayer = null;
                playVideo();


                handler.removeMessages(HIDDLECONTROL);
                handler.sendEmptyMessageDelayed(HIDDLECONTROL, 4000);
                break;

            case R.id.btn_open_comment:
                //打开popupwindow

                showPopwindows();
                break;
            case R.id.iv_bottom_back:
                finish();
                break;

            case R.id.to_comments:


                break;


        }

    }

    private void showPopwindows() {
        View inflate = View.inflate(this, R.layout.popup_comment, null);
        et_com = (EditText) inflate.findViewById(R.id.et_com);
        final TextView pop_fontCount = (TextView) inflate.findViewById(R.id.pop_fontCount);
        TextView pop_cancle = (TextView) inflate.findViewById(R.id.pop_cancle);
        final TextView pop_publish = (TextView) inflate.findViewById(R.id.pop_publish);


        popupInputMethodWindow(); //显示输入法
        final PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setContentView(inflate);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable((new BitmapDrawable()));
        View view = View.inflate(this, R.layout.activity_noskin_vp, null);
        //让popupwindow不被输入法隐藏
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        backgroundAlpha(0.5f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
        pop_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        pop_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发布评论、
                String comment = et_com.getText().toString();
                if (!StringUtil.isEmpty(comment)) {
                    //判断是否为登录状态
               String status = (String) SPUtils.get(NoskinVpActivity.this, MyApplication.LOGINSTATUES,"0");
               if(status.equals("1")){

                   popupWindow.dismiss();
                    //发表评论
                   SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM-dd HH:mm" );
                   String time = simpleDateFormat.format(new Date());
                   String name = (String) SPUtils.get(NoskinVpActivity.this,MyApplication.NICKNAME,"");
                   final CommentLists.ResultBean resultBean = new CommentLists.ResultBean("",comment,time,name);
                   if(result==null){

                      result=new ArrayList<CommentLists.ResultBean>();
                   }





                   OkHttpUtils.post().url(Constants.POSTCOMMENTS+datas.getVid()+".html").addHeader("Authorization","Bearer "+token).
                              addParams("uid",uid).addParams("text",comment).build().execute(new StringCallback() {
                          @Override
                          public void onError(Call call, Exception e, int id) {
                              Log.i("TAG","评论失败"+e.getMessage());
                          }

                          @Override
                          public void onResponse(String response, int id) {
                              result.add(0,resultBean);
                              Log.i("TAG","评论"+result);
                              VideoDetailAdapter adapter = new VideoDetailAdapter(NoskinVpActivity.this, datas, guessLikeVideos, result);
                              mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
                              lrecyclerView.setAdapter(mLRecyclerViewAdapter);
                          }
                      });


               }else{
                   //打开登录页面
                   startActivity(new Intent(NoskinVpActivity.this, LoginActivity.class));
               }

                } else {
                    Toast.makeText(NoskinVpActivity.this, "请填写你的评论", Toast.LENGTH_SHORT).show();
                }
            }
        });

        et_com.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {

                    pop_publish.setTextColor(getResources().getColor(R.color.yellow_publish));
                    pop_fontCount.setText(s.length() + "");
                } else {
                    pop_publish.setTextColor(getResources().getColor(R.color.gray_9a9a9a));
                }
            }
        });

    }

    private void getNetState() {

        boolean connected = NetUtils.isConnected(this);
        if (connected) {
            ll_bottom.setVisibility(View.VISIBLE);
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
            ll_bottom.setVisibility(View.GONE);


        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            if (isFullScreen) {

                outFullScreen();
                isFullScreen = false;
            }

        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 弹出输入法窗口
     */
    private void popupInputMethodWindow() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                InputMethodManager systemService = (InputMethodManager) et_com.getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
                systemService.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }).start();
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();
        OkHttpUtils.getInstance().cancelTag(this);

    }



}