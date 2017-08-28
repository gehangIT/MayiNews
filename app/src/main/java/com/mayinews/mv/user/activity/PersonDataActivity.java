package com.mayinews.mv.user.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.mv.MyApplication;
import com.mayinews.mv.R;
import com.mayinews.mv.user.fragment.UserFragment;
import com.mayinews.mv.utils.SPUtils;
import com.mayinews.mv.utils.ScreenUtils;
import com.nanchen.compresshelper.CompressHelper;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class PersonDataActivity extends Activity implements View.OnClickListener {

    private static final int TAKE_PHOTO = 1;

    private static final int CHOOSE_FROM_ALBUM = 2;
    //    private static final int CHANGENICKNAME = 3;
//    private static final int CHOOSEGENDER = 4;
    Uri imageUri;
    private AlertDialog.Builder builder;
    private View view;
    private AlertDialog dialog;

    @BindView(R.id.tv_back)
    ImageView tvBack;     //左上角返回按键

    @BindView(R.id.ll_headIcon)
    LinearLayout llHeadIcon;   //个人头像ll

    @BindView(R.id.iv_headIcon)
    CircleImageView ivheadIcon; //个人头像控件
    @BindView(R.id.tv_nickName)
    TextView tvNickName;       //昵称textVIEW
    @BindView(R.id.ll_nickName)
    LinearLayout llNickName;    //昵称ll

    @BindView(R.id.tv_gender)
    TextView tvGender;          //性别TextView
    @BindView(R.id.ll_gender)
    LinearLayout llGender;      //LinearLayout
    @BindView(R.id.tv_signature)
    TextView tvSignature;          //签名TextView
    @BindView(R.id.ll_signature)
    LinearLayout llSignature;       //签名LinearLayout


    //用来保存个人资料的 图片地址， 昵称，签名，性别 ，
    File outputImage;//保存拍摄图片的File
    String UserNickName;//昵称
    String UserSignature; //签名
    String UserGender;//性别

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_data);
        ButterKnife.bind(this);
        view = LayoutInflater.from(this).inflate(R.layout.alertdialog, null);
        initData();


    }


    private void initData() {
        if (UserFragment.isLogin) {
            //登录的状态，请求网络显示个人的信息

        } else {
            //非登录状态


        }


    }


    @OnClick({R.id.tv_back, R.id.ll_headIcon, R.id.ll_nickName, R.id.ll_gender, R.id.ll_signature})
    public void ButterKnife_Onclick(View v) {
        switch (v.getId()) {

            case R.id.tv_back:
                finish();
                break;
            case R.id.ll_headIcon:  //设置个人的头像
//                showAlertDialog();

                //进入设置头像界面
                Intent iconIntent=new Intent(this,PersonIconActivity.class);
                startActivity(iconIntent);
                break;


            case R.id.ll_nickName:  //进入个人昵称的页面
                Intent intent = new Intent(this, PersonNickNameActivity.class);
                startActivity(intent);


                break;
            case R.id.ll_gender:    //进入选择性别的页面
                Intent genderIntent = new Intent(this, PersonGenderActivity.class);

                startActivity(genderIntent);
                break;
            case R.id.ll_signature:  //进入个人签名的页面
                Intent SignatureIntent = new Intent(this, PersonSignatureActivity.class);
                startActivity(SignatureIntent);
                break;
        }

    }


    private void showAlertDialog() {

        if (builder == null) {


            builder = new AlertDialog.Builder(this);
            view = LayoutInflater.from(this).inflate(R.layout.alertdialog, null);
            dialog = builder.create();
            Window window = dialog.getWindow();
            dialog.setView(view);
            window.setWindowAnimations(R.style.dialog_style);
            window.setBackgroundDrawable(null);
            window.setGravity(Gravity.BOTTOM);//底部出现
            window.setBackgroundDrawableResource(android.R.color.transparent);

        }

        if (dialog.isShowing()) {
            dialog.dismiss();
        } else {
            dialog.show();
            WindowManager windowManager = getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = (int) (display.getWidth()); //设置宽度
            dialog.getWindow().setAttributes(lp);
        }

        TextView chose_picture = (TextView) view.findViewById(R.id.chose_picture);
        TextView record = (TextView) view.findViewById(R.id.record);
        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        record.setText("拍照");
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
                outputImage = new File(getExternalCacheDir(), "head.jpg");

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

            if (requestCode == TAKE_PHOTO) {


                File newFile = new CompressHelper.Builder(this)
                        .setMaxWidth(720)  // 默认最大宽度为720
                        .setMaxHeight(960) // 默认最大高度为960
                        .setQuality(80)    // 默认压缩质量为80
                        .setFileName("head") // 设置你需要修改的文件名
                        .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                        .setDestinationDirectoryPath(outputImage.getParent())
                        .build()
                        .compressToFile(outputImage);
                Bitmap bitmap = BitmapFactory.decodeFile(newFile.getAbsolutePath());//解析为bitmap
                Log.i("TAG", "压缩后的文件地址为=" + newFile.getAbsolutePath());
                ivheadIcon.setImageBitmap(bitmap);//获得的数据显示在ImageView中


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
            File newFile = new CompressHelper.Builder(this)
                    .setMaxWidth(720)  // 默认最大宽度为720
                    .setMaxHeight(960) // 默认最大高度为960
                    .setQuality(80)    // 默认压缩质量为80
                    .setFileName("head") // 设置你需要修改的文件名
                    .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                    .setDestinationDirectoryPath(getExternalCacheDir().getAbsolutePath())
                    .build()
                    .compressToFile(outputImage);
            Bitmap bitmap = BitmapFactory.decodeFile(newFile.getAbsolutePath());//解析为bitmap
            Log.i("TAG", "压缩后的文件地址为=" + newFile.getAbsolutePath());
            ivheadIcon.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String nickName = (String) SPUtils.get(this, MyApplication.NICKNAME, "");
        String signature = (String) SPUtils.get(this, MyApplication.SIGNATURE, "");
        String gender = (String) SPUtils.get(this, MyApplication.GENDER, "");
        tvNickName.setText(nickName);
        tvSignature.setText(signature);
        if (gender.equals("0")) {
            tvGender.setText("男");
        }
        if (gender.equals("1")) {
            tvGender.setText("女");
        }

        //decodeFile(+"head.jpg");
        Bitmap bitmap = BitmapFactory.decodeFile(getExternalCacheDir().getAbsolutePath()+"/head.jpeg");
        Log.i("TAG","bitmap"+bitmap);
        if(bitmap!=null){

            ivheadIcon.setImageBitmap(bitmap);

        }else{
            String iconurl= (String) SPUtils.get(this, MyApplication.USERICON, "");
            if(!iconurl.equals("")){
                Glide.with(this).load(buildGlideUrl(iconurl)).into(ivheadIcon);
            }


        }


    }


    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://www.mayinews.com").build());
        }
    }
}