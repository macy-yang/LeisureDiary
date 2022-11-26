package com.scujcc.leisurediary.main;
//闲游日记
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scujcc.leisurediary.R;
import com.scujcc.leisurediary.ui.FootBar;

public class MainActivity extends FragmentActivity {
    private HomeFragment homeFragment;
    private SettingFragment settingFragment;

    FootBar homeFragmentLayout;
    FootBar settingFragmentLayout;
    FragmentManager fragmentManager;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        initBottomLayout();
        FloatingActionButton add_diary = (FloatingActionButton)findViewById(R.id.add_diary);
        add_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddDiaryActivity.class);
                startActivity(intent);
            }
        });
    }

    //为底部导航栏设置默认状态和选中状态的文字和图片样式
    private void initBottomLayout() {
        homeFragmentLayout = findViewById(R.id.homepage);
        settingFragmentLayout = findViewById(R.id.settingpage);

        homeFragmentLayout.setNormalImage(R.drawable.homepage_normal);
        homeFragmentLayout.setFocusedImage(R.drawable.homepage_focused);
        homeFragmentLayout.setFocused(true);
        homeFragmentLayout.setTvText("首页");

        settingFragmentLayout.setNormalImage(R.drawable.mine_normal);
        settingFragmentLayout.setFocusedImage(R.drawable.mine_focused);
        settingFragmentLayout.setTvText("我的");
        settingFragmentLayout.setFocused(false);

        homeFragmentLayout.setOnClickListener(new mClick());
        settingFragmentLayout.setOnClickListener(new mClick());

        frameLayout = findViewById(R.id.frameLayout_container);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        transaction.add(R.id.frameLayout_container,homeFragment);
        transaction.commit();

    }

    //点击菜单切换界面
    private class mClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (v.getId()){
                case R.id.homepage:
                    homeFragmentLayout.setFocused(true);
                    settingFragmentLayout.setFocused(false);
                    if(homeFragment == null){
                        homeFragment = new HomeFragment();
                        transaction.replace(R.id.frameLayout_container,homeFragment);
                    }else{
                        transaction.replace(R.id.frameLayout_container,homeFragment);
                    }
                    break;
                case R.id.settingpage:
                    homeFragmentLayout.setFocused(false);
                    settingFragmentLayout.setFocused(true);
                    if(settingFragment == null){
                        settingFragment = new SettingFragment();
                        transaction.replace(R.id.frameLayout_container, settingFragment);
                    }else{
                        transaction.replace(R.id.frameLayout_container,settingFragment);
                    }
                    break;


            }
            transaction.commit();
        }
    }
}

