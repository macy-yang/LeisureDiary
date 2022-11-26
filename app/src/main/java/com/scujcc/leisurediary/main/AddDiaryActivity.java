package com.scujcc.leisurediary.main;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scujcc.leisurediary.R;
import com.scujcc.leisurediary.login.LoginActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

/**
 * 添加游记
 * @author 杨梦婷
 * time:2022/11/18
 */
public class AddDiaryActivity extends AppCompatActivity {
    private static String[] items = new String[]{
            "拍照",
            "从相册中选择",
    };
    public static final int TAKE_PHOTO = 1;//声明一个请求码，用于识别返回的结果
    private static final int SCAN_OPEN_PHONE = 2;// 相册
    private Uri imageUri;
    public String path = null;
    Bitmap bitmap;
    public String picpath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);

        //自动显示时间
        EditText diary_time = (EditText) findViewById(R.id.diary_time);
        Calendar now = new GregorianCalendar();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHH", Locale.getDefault());
        diary_time.setText(simpleDate.format(now.getTime()));
        //TODO 自动显示地点
        EditText diary_location = (EditText) findViewById(R.id.diary_location);
        //游记文字
        EditText diary_content = (EditText) findViewById(R.id.diary_content);
        //竖向滑动
        diary_content.setHorizontallyScrolling(true);
        //滑动到最后一行添加文字
        diary_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        diary_content.setSelection(diary_content.getText().length(), diary_content.getText().length());
        //拍照或者选取照片界面
        FloatingActionButton add_diary_image = (FloatingActionButton)findViewById(R.id.add_diary_image);
        add_diary_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                requestPermission();
                askPermission();
                choosePic();

            }
        });

    }
    private void askPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        }, 0);

    }
    private void choosePic() {
        FloatingActionButton add_diary_image = (FloatingActionButton)findViewById(R.id.add_diary_image);
        AlertDialog.Builder builder = new AlertDialog.Builder(AddDiaryActivity.this)
                .setTitle("请选择图片")//设置对话框 标题
                .setItems(items, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        add_diary_image.setVisibility(View.GONE);
                        if (which == 0) {
                            openCamera();
                        } else {
                            openGallery();
                        }
                        return;
                    }
                });
        builder.create()
                .show();
    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //intent.setType("image/*");
        startActivityForResult(intent, SCAN_OPEN_PHONE);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void openCamera() {
        //时间命名
        String imageName = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
//        File outputImage=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/data/com.example.woundapplication/"+imageName+".jpg");

        //TODO 写入关联缓存改写成写入本地内存
        // 获取当前App的私有下载目录
//        String path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/";
        File outputImage = new File(getExternalCacheDir(), imageName + ".jpg");
//        File outputImage = new File(path, imageName + ".jpg");

        Objects.requireNonNull(outputImage.getParentFile()).mkdirs();
//        Log.e("", outputImage.getAbsolutePath());
                /*
                创建一个File文件对象，用于存放摄像头拍下的图片，
                把它存放在应用关联缓存目录下，调用getExternalCacheDir()可以得到这个目录，为什么要
                用关联缓存目录呢？由于android6.0开始，读写sd卡列为了危险权限，使用的时候必须要有权限，
                应用关联目录则可以跳过这一步
                 */
        try//判断图片是否存在，存在则删除在创建，不存在则直接创建
        {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            boolean a = outputImage.createNewFile();
            Log.e("createNewFile", String.valueOf(a));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24)
            //判断安卓的版本是否高于7.0，高于则调用高于的方法，低于则调用低于的方法
            //把文件转换成Uri对象
                    /*
                    因为android7.0以后直接使用本地真实路径是不安全的，会抛出异常。
                    FileProvider是一种特殊的内容提供器，可以对数据进行保护
                     */ {
            imageUri = FileProvider.getUriForFile(AddDiaryActivity.this,
                    "com.buildmaterialapplication.fileprovider", outputImage);
            //对应Mainfest中的provider
//            imageUri=Uri.fromFile(outputImage);
            path = imageUri.getPath();
            Log.e(">7:", path);
        } else {
            imageUri = Uri.fromFile(outputImage);
            path = imageUri.getPath();

            Log.e("<7:", imageUri.getPath());

        }

        //使用隐示的Intent，系统会找到与它对应的活动，即调用摄像头，并把它存储
        Intent intent0 = new Intent("android.media.action.IMAGE_CAPTURE");
        intent0.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent0, TAKE_PHOTO);
    }
    @SuppressLint("SetTextI18n")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO 根据picpath显示图片
        ImageView img_result = (ImageView) findViewById(R.id.pic);
        img_result.setVisibility(View.VISIBLE);

        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //将图片解析成Bitmap对象，并把它显现出来
//                    String filePath = getFilesDir().getAbsolutePath()+"/image.jpeg";
//                    bitmap = BitmapFactory.decodeFile(filePath);
                    //注意bitmap，后面再decode就会为空
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
//                        bitmap = BitmapFactory.decodeFile(picpath);
                    picpath = imageUri.getPath().toString();
                    Log.e("", imageUri.getAuthority());

                    Log.e("picpath", picpath);
                    @SuppressLint("SdCardPath") String fileName = picpath;
                    //将拍摄的图片显示出来
                    img_result.setImageBitmap(bitmap);
                    img_result.invalidate();
                }

                break;
            case SCAN_OPEN_PHONE:
                if (resultCode == RESULT_OK) {

                    Uri selectImage = data.getData();
                    String[] FilePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectImage,
                            FilePathColumn, null, null, null);
                    cursor.moveToFirst();
                    //从数据视图中获取已选择图片的路径
                    int columnIndex = cursor.getColumnIndex(FilePathColumn[0]);
                    picpath = cursor.getString(columnIndex);
                    Log.e("picpath", picpath);
                    cursor.close();
                    bitmap = BitmapFactory.decodeFile(picpath);
                    img_result.setImageBitmap(bitmap);
                    img_result.invalidate();
                }
                break;
            default:
                break;
        }
    }

    //焦点消失，外部键盘消失
    /**
     * 获取当前点击位置是否为EditText
     * @param view 焦点所在View
     * @param event 触摸事件
     * @return
     */
    public  boolean isClickEt(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            view.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            //此处根据输入框左上位置和宽高获得右下位置
            int bottom = top + view.getHeight();
            int right = left + view.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            //获取当前获得当前焦点所在View
            View view = getCurrentFocus();
            if (isClickEt(view, event)) {

                //如果不是edittext，则隐藏键盘

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    //隐藏键盘
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(event);
        }
        /**
         * 看源码可知superDispatchTouchEvent  是个抽象方法，用于自定义的Window
         * 此处目的是为了继续将事件由dispatchTouchEvent(MotionEvent event)传递到onTouchEvent(MotionEvent event)
         * 必不可少，否则所有组件都不能触发 onTouchEvent(MotionEvent event)
         */
        if (getWindow().superDispatchTouchEvent(event)) {
            return true;
        }
        return onTouchEvent(event);
    }

    //相机权限申请
    public static final int CAMERA_REQ_CODE = 111;

    private void requestPermission() {
        // 判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // 如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                Toast.makeText(AddDiaryActivity.this,"请进入设置-应用管理-打开相机权限",Toast.LENGTH_SHORT).show();

            } else {
                // 进行权限请求
                ActivityCompat.requestPermissions(
                                this,
                                new String[]{Manifest.permission.CAMERA},
                                CAMERA_REQ_CODE);
            }
        }

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == CAMERA_REQ_CODE) {
//            // 如果请求被拒绝，那么通常grantResults数组为空
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // 申请成功，进行相应操作
//                Intent intent = new Intent(AddDiaryActivity.this, CameraActivity.class);
//                startActivity(intent);
//            } else {
//                // 申请失败，可以继续向用户解释。
//                Toast.makeText(AddDiaryActivity.this, "没有相机权限,您可能无法正常使用本应用", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}