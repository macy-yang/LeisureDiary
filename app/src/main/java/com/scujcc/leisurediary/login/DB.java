package com.scujcc.leisurediary.login;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * SQLite增删查改数据库
 * @author 杨梦婷
 * time:2022/11/24
 */
public class DB extends SQLiteOpenHelper {
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    private SQLiteDatabase db;
    public DB(Context context){
        super(context, "TEST1.db", null, 1);
        db = this.getWritableDatabase();
    }
    public boolean add(String name, String psw){
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("psw", psw);
        long i = db.insert("users", null, values);
        if (i > 0){
            Log.d("", "插入成功");
            return true;
        }
        return false;
    }
    public boolean del(String name){
        long i = db.delete("users", "name = ?", new String[]{name});
        if (i > 0){
            Log.d("", "删除成功");
            return true;
        }
        return false;
    }
    //通过账号name来修改密码psw
    public boolean change(String name, String NewPsw){
        ContentValues values = new ContentValues();
        values.put("psw", NewPsw);
        long i = db.update("users", values, "name = ?", new String[]{name});
        if (i > 0){
            Log.d("", "修改成功");
            return true;
        }
        return false;
    }
    //查看所有
    public ArrayList getAll(){
        ArrayList array = new ArrayList();
        Cursor cursor = db.query("users", null, null,null, null, null, null);
        while (cursor.moveToNext()){
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String psw = cursor.getString(cursor.getColumnIndex("psw"));
            Users user = new Users(name, psw);
            array.add(user);
        }
        return array;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table users(name text primary key, psw text not null)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
