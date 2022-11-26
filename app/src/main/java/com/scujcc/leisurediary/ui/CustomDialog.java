package com.scujcc.leisurediary.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scujcc.leisurediary.R;

/**
 * 自定义dialog——继承Dialog + 实现View.OnClickListener接口
 * 1、保证dialog文字可修改  2、监听dialog的cacel和confirm两个按钮事件
 * @author 杨梦婷
 * time:2022/11/16
 */
public class CustomDialog extends Dialog implements View.OnClickListener {
    //1.1声明控件、字符串
    private TextView title, content, confirm, cancel;
    private String sTitle, sContent, sConfirm, sCancel;
    //2.1声明两个监听器cancelListener, confirmListener
    private View.OnClickListener confirmListener, cancelListener;

    //1.3、generate自动生成四个字符串的set方法（dialog中文字可以编写）,使用链式调用时void改成CustomDialog并返回this
    public CustomDialog setsTitle(String sTitle) {
        this.sTitle = sTitle;
        return this;
    }

    public CustomDialog setsContent(String sContent) {
        this.sContent = sContent;
        return this;
    }

    //2.2写入两个监听器 + 这两个监听器分别传给上面声明的两个监听器
    public CustomDialog setsConfirm(String sConfirm, View.OnClickListener listener) {
        this.sConfirm = sConfirm;
        this.confirmListener = listener;
        return this;
    }

    public CustomDialog setsCancel(String sCancel, View.OnClickListener listener) {
        this.sCancel = sCancel;
        this.cancelListener = listener;
        return this;
    }

    //重写CustomDialog和onCreate方法
    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog);

        //点击空白处dialog消失
        setCancelable(true);

        //自定义dialog宽度
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();//获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.alpha = 0.9f;
        Point size = new Point();
        d.getSize(size);
        p.width= (int)((size.x)*0.7);//设置为屏幕的0.7倍宽度
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setBackgroundDrawableResource(R.drawable.dialog_circle_shape);
        getWindow().setAttributes(p);

        //1.2、在onCreate找到四个控件
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        cancel = findViewById(R.id.cancel);
        confirm = findViewById(R.id.confirm);

        //1.4、字符串传入布局，字符串非空则赋值给布局text
        if(!TextUtils.isEmpty(sTitle)){
            title.setText(sTitle);
        }
        if(!TextUtils.isEmpty(sContent)){
            content.setText(sContent);
        }
        if(!TextUtils.isEmpty(sCancel)){
            cancel.setText(sCancel);
        }
        if(!TextUtils.isEmpty(sConfirm)){
            confirm.setText(sConfirm);
        }

        //2.3在onCreat方法中设置监听器
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.confirm:
                if(confirmListener != null){
                    confirmListener.onClick(view);
                }
                break;
            case R.id.cancel:
                if(cancelListener != null){
                    cancelListener.onClick(view);
                }
                break;
        }
    }
}
