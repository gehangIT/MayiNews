package com.mayinews.mv.user.activity;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aliyun.common.utils.ToastUtil;
import com.mayinews.mv.R;
import com.mayinews.mv.discovery.activity.PublishActivity;
import com.mayinews.mv.user.adapter.MyGridViewAdapter;
import com.mayinews.mv.user.adapter.MyViewPagerAdapter;
import com.nanchen.compresshelper.CompressHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonIconActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean isPjoto=false;//标记是否为照相或者相册来查看头像
    private Bitmap testbitmap;//没保存之前显示的头像
    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_FROM_ALBUM = 2;
    private LinearLayout group;//圆点指示器
    private ViewPager viewPager;
    ImageView[] ivPoints;//小圆点
    File outputImage;//保存拍摄图片的File
    Uri imageUri;
    //图片的资源
    private int[] images = {R.drawable.mu, R.drawable.mv, R.drawable.mw, R.drawable.mx, R.drawable.my
            , R.drawable.mz, R.drawable.mt, R.drawable.n0, R.drawable.n1, R.drawable.n2, R.drawable.n3,
            R.drawable.n4, R.drawable.n5, R.drawable.n6, R.drawable.n8, R.drawable.n8};
    private List<View> viewPagerList;   //将每个GridView添加到ViewPager的适配器中
    private int totalPage = 2; //总的页数
    private int mPageSize = 8; //每页显示的最大的数量

    private ImageView circleView;  //头像
    private ImageView titltBack;   //返回
    private TextView save;        //保存

    private Bitmap HeadBitmap;//当前头像的bitmap


    private AlertDialog.Builder builder;
    private View view;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_icon);
        initView();
        for (int i = 0; i < 2; i++) {
            GridView gridView = (GridView) View.inflate(this, R.layout.icon_gridview, null);
            MyGridViewAdapter adapter = new MyGridViewAdapter(this, images, i, mPageSize);
            gridView.setAdapter(adapter);
            //点击事件

            adapter.setOnChooseListener(new MyGridViewAdapter.OnchooseListener() {
                @Override
                public void onChoose(int position) {
                    ToastUtil.showToast(PersonIconActivity.this, "123");
                    //设置图像
                    Drawable drawable = getResources().getDrawable(images[position]);
                    testbitmap = ((BitmapDrawable) drawable).getBitmap();
                    circleView.setImageBitmap(testbitmap);

                }
            });
            viewPagerList.add(gridView);
        }
//设置ViewPager适配器
        viewPager.setAdapter(new MyViewPagerAdapter(viewPagerList));

        //添加小圆点
        final ImageView[] ivPoints = new ImageView[totalPage];
        for (int i = 0; i < totalPage; i++) {
            //循坏加入点点图片组
            ivPoints[i] = new ImageView(this);
            if (i == 0) {
                ivPoints[i].setImageResource(R.drawable.page_focuese);
            } else {
                ivPoints[i].setImageResource(R.drawable.page_unfocused);
            }
            ivPoints[i].setPadding(8, 8, 8, 8);
            group.addView(ivPoints[i]);
        }
        //设置ViewPager的滑动监听，主要是设置点点的背景颜色的改变
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                //currentPage = position;
                for (int i = 0; i < totalPage; i++) {
                    if (i == position) {
                        ivPoints[i].setImageResource(R.drawable.page_focuese);
                    } else {
                        ivPoints[i].setImageResource(R.drawable.page_unfocused);
                    }
                }
            }
        });
    }

    private void initView() {
        circleView = (ImageView) findViewById(R.id.circleView);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPagerList = new ArrayList<>();
        group = (LinearLayout) findViewById(R.id.points);
        titltBack = (ImageView) findViewById(R.id.iv_back);
        save = (TextView) findViewById(R.id.tv_save);
        titltBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeadBitmap=testbitmap;
                if(HeadBitmap==null){

                    Drawable drawable = getResources().getDrawable(images[0]);
                    HeadBitmap = ((BitmapDrawable) drawable).getBitmap();
                }else{





                //保存头像
                // 创建一个位于SD卡上的文件
                File file = new File(getExternalCacheDir(),
                        "head.jpeg");
                if(file.exists()){

                    file.delete();
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                FileOutputStream out = null;

                // 打开指定文件输出流
                try {
                    out = new FileOutputStream(file);
                    // 将位图输出到指定文件
                    HeadBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                            out);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                }
                 finish();




            }

        });

        circleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                       //展示
                showAlertDialog();
            }
        });



    }
    private void showAlertDialog() {

        if(builder==null){
            builder=new android.support.v7.app.AlertDialog.Builder(PersonIconActivity.this);
            view=LayoutInflater.from(PersonIconActivity.this).inflate(R.layout.alertdialog,null);
            dialog=builder.create();
            Window window = dialog.getWindow();
            dialog.setView(view);
            window.setWindowAnimations(R.style.dialog_style);

            window.setGravity(Gravity.BOTTOM);//底部出现
            window.setBackgroundDrawableResource(android.R.color.transparent);

        }

        if(dialog.isShowing()){
            dialog.dismiss();
        }else{
            dialog.show();
            WindowManager windowManager = getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = (int)(display.getWidth()); //设置宽度
            dialog.getWindow().setAttributes(lp);
        }

        TextView chose_picture = (TextView) view.findViewById(R.id.chose_picture);
        TextView record = (TextView) view.findViewById(R.id.record);
        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        chose_picture.setOnClickListener(this);
        record.setOnClickListener(this);
        cancle.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chose_picture:
                // 从相册中去获取
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_FROM_ALBUM); //打开相册
                dialog.dismiss();


                break;
            case R.id.record:

                //创建File文件，用于储存拍照后的照片
                //getExternalCacheDir()关联缓存目录（专门存放缓存的目录）
                //具体路径/sdcard/Android/data/包名/cache
                //Android6.0，SD卡被列为危险权限，放在其他目录就要进行权限处理
                outputImage = new File(getExternalCacheDir(), "output.jpeg");

                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Android7.0 调用FileProvider.getUriForFile()封装成Uri的字符串，接收3个参数,第一个:上下文对象,第二个：任意唯一字符串，第三个，File对象
                //7.0以下调用Uri.fromFile()将File对象转化Uri
                //FileProvider是一种特殊的内容提供器，对数据进行保护，选择性的封装过的Uri共享给外部，提高应用安全性。
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(this, "com.example.gh", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }


                //启动相机
                Intent intent1 = new Intent("android.media.action.IMAGE_CAPTURE");
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent1, TAKE_PHOTO);
                dialog.dismiss();
                break;

            case R.id.cancle:       //取消

                dialog.dismiss();
                break;

        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
                   isPjoto=true;
            if (requestCode == TAKE_PHOTO) {
                Log.i("TAG", "压缩文件地址为=" + outputImage.getAbsolutePath());
                File newFile = new CompressHelper.Builder(this)
                        .setMaxWidth(720)  // 默认最大宽度为720
                        .setMaxHeight(960) // 默认最大高度为960
                        .setQuality(80)    // 默认压缩质量为80
                        .setFileName("head2") // 设置你需要修改的文件名
                        .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                        .setDestinationDirectoryPath(getExternalCacheDir().getAbsolutePath())
                        .build()
                        .compressToFile(outputImage);
                testbitmap = BitmapFactory.decodeFile(newFile.getAbsolutePath());//解析为bitmap
////
                circleView.setImageBitmap(testbitmap);//获得的数据显示在ImageView中


            }

            if (requestCode == CHOOSE_FROM_ALBUM) {
                //相册的返回
                //判断手机系统版本号
                if (Build.VERSION.SDK_INT >= 19) {
                    //4.4以上系统处理图片
                    handleImageOnKitKat(data);
                } else {
                    //4.4以下系统处理图片
                    handleImageBeforeKitKat(data);
                }

            }


        }
    }



    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的Uri,则通过 document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; //解析出数字格式的Id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.provides.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的Uri,直接获取图片路径
            imagePath = uri.getPath();
        }
        displayImage(imagePath); //根据图片路径显示图片
    }


    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }


    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            outputImage = new File(imagePath);   //保存相册的地址
//            File newFile = new CompressHelper.Builder(this)
//                    .setMaxWidth(720)  // 默认最大宽度为720
//                    .setMaxHeight(960) // 默认最大高度为960
//                    .setQuality(80)    // 默认压缩质量为80
//                    .setFileName("head") // 设置你需要修改的文件名
//                    .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
//                    .setDestinationDirectoryPath(getExternalCacheDir().getAbsolutePath())
//                    .build()
//                    .compressToFile(outputImage);
            File newFile = CompressHelper.getDefault(getApplicationContext()).compressToFile(outputImage);
            testbitmap = BitmapFactory.decodeFile(newFile.getAbsolutePath());//解析为bitmap
            Log.i("TAG", "压缩后的文件地址为=" + newFile.getAbsolutePath());
            circleView.setImageBitmap(testbitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(!isPjoto) {
            Bitmap bitmap = BitmapFactory.decodeFile(getExternalCacheDir().getAbsolutePath() + "/head.jpeg");
            if (bitmap != null) {
                circleView.setImageBitmap(bitmap);
            }
        }
    }
}
