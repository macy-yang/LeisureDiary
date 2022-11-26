package com.scujcc.leisurediary.login;

import androidx.annotation.Nullable;
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
import com.scujcc.leisurediary.main.AddDiaryActivity;
import com.scujcc.leisurediary.main.MainActivity;

/**
 * 登录注册页面：SignActivity页面注册后把信息回调到LoginActivity页面
 * @author 杨梦婷
 * time:2022/11/24
 */
public class LoginActivity extends AppCompatActivity {
    public int Sign_RequestCode = 1;
    Button sign;
    Button login;
    EditText name;
    EditText psw;
    String _name, _psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sign = findViewById(R.id.sign);
        login = findViewById(R.id.login);
        name = findViewById(R.id.name);
        psw = findViewById(R.id.psw);

        //注册
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, SignActivity.class);
                startActivityForResult(intent, Sign_RequestCode);
            }
        });
        //登录之后跳到欢迎界面
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_name.equals(name.getText().toString()) && _psw.equals(psw.getText().toString())){
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, WelcomActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this, "登录失败，账号密码错误！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Sign_RequestCode){
            if (resultCode == RESULT_OK){
                String name1 = data.getStringExtra("name");
                String psw1 = data.getStringExtra("psw");
                name.setText(name1);
                psw.setText(psw1);
                _name = name1;
                _psw = psw1;
            }
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

}