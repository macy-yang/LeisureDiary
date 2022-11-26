package com.scujcc.leisurediary.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.scujcc.leisurediary.R;
import com.scujcc.leisurediary.main.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 欢迎界面
 * @author 杨梦婷
 * time:2022/11/24
 */
public class WelcomActivity extends AppCompatActivity implements View.OnClickListener{
    private int recLen = 3;//跳过倒计时提示3s
    private Button mBtn;
    Timer timer = new Timer();
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        //TODO 倒计时3s进入MainActivity
        //设置当前的窗体为全屏显示
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, flag);
        initView();
        timer.schedule(task, 1000, 1000);//等待时间1s，停顿时间1s
        //正常情况下不点击跳过
        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                //跳转到首界面
                Intent intent = new Intent(WelcomActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);//延迟3s后发送handler信息

    }

    //跳过监听
    private void initView(){
        mBtn = findViewById(R.id.next);
        mBtn.setOnClickListener(this);
    }
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recLen--;
                    mBtn.setText("跳过" + recLen);//在控件上显示距离跳转的剩余时间
                    if (recLen < 0){
                        timer.cancel();
                        mBtn.setVisibility(View.GONE);//倒计时到0隐藏字体
                    }
                }
            });
        }
    };
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next:
                //发生点击实践时直接跳转到首界面
                Intent intent = new Intent(WelcomActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                if (runnable != null){
                    handler.removeCallbacks(runnable);
                }
                break;
            default:
                break;
        }
    }
}