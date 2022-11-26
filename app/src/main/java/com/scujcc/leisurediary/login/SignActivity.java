package com.scujcc.leisurediary.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.scujcc.leisurediary.R;

import java.util.ArrayList;

/**
 * 注册页面
 * @author 杨梦婷
 * time:2022/11/24
 */
public class SignActivity extends AppCompatActivity {
    Button submit;
    Button back;
    EditText name;
    EditText psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        submit = findViewById(R.id.submit);
        back = findViewById(R.id.back);
        name = findViewById(R.id.sign_name);
        psw = findViewById(R.id.sign_psw);
        DB db = new DB(this);

        //注册信息把提交到数据库
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = name.getText().toString();
                String p = psw.getText().toString();
                if (db.add(n, p)){
                    Toast.makeText(SignActivity.this, "添加用户成功!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SignActivity.this, "添加用户失败!", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        //TODO 查看所有用户seeall
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //content.setText("");//查看所有用户，所以先要清空一下内容
//                ArrayList a = db.getAll();
//                for (Object b:
//                     a) {
//                    /**
//                     * content是布局文件中的scrollView
//                     * String n = content.getText().toString();
//                     *
//                     */
//                    //String n = content.getText().toString();
//                    //String str = n + ((Users)b).toString() + "\n";
//                    //content.setText(str);
//                }
//            }
//        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("psw", psw.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
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
}