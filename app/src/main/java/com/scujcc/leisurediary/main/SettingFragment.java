package com.scujcc.leisurediary.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.scujcc.leisurediary.R;

/**
 * 我的fragment
 * @author 杨梦婷
 * time:2022/11/16
 */
public class SettingFragment extends Fragment {
    private ImageView headPic;
    private TextView userName;
    private TextView diaryNum;
    private Button backLogin;
    private Button signOut;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting,container, false);
        /**
         * TODO
         * 选择头像
         * 修改用户名
         * 游记篇数
         * 返回登录界面
         * 退出APP
         */
        headPic = view.findViewById(R.id.head_pic);
        userName = view.findViewById(R.id.user_name);
        diaryNum = view.findViewById(R.id.diary_num);
        backLogin = view.findViewById(R.id.back_login);
        signOut = view.findViewById(R.id.sign_out);
        headPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}