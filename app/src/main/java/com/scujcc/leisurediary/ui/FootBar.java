package com.scujcc.leisurediary.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.scujcc.leisurediary.R;

/**
 * 底部导航栏
 * @author 杨梦婷
 * time:2022/11/16
 */
public class FootBar extends LinearLayout {
    private int normalIcon;
    private int focusedIcon;
    private boolean isFocused;
    private ImageView ivIcon;
    private TextView tvText;
    public FootBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.foot_bar,this);
        ivIcon = findViewById(R.id.bottom_icon);
        tvText = findViewById(R.id.bottom_text);

    }

    public void setNormalImage(int normalIcon){
        this.normalIcon = normalIcon;
        ivIcon.setImageResource(normalIcon);

    }
    public void setFocusedImage(int focusedIcon){
        this.focusedIcon = focusedIcon;

    }
    public void setTvText(String text){
        tvText.setText(text);
    }
    public void setFocused(boolean isFocused){
        this.isFocused = isFocused;
        if(isFocused){
            ivIcon.setImageResource(focusedIcon);
            tvText.setTextColor(Color.parseColor("#02b5bc"));
        }else{
            ivIcon.setImageResource(normalIcon);
            tvText.setTextColor(Color.BLACK);
        }
    }

}