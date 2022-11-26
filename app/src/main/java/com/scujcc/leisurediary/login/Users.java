package com.scujcc.leisurediary.login;

/**
 * 用户变量
 * @author 杨梦婷
 * time:2022/11/25
 */
public class Users {
    String name;
    String psw;

    public Users(String name, String psw) {
        this.name = name;
        this.psw = psw;
    }

    @Override
    public String toString() {
        return name + ','+ psw ;
    }
}
