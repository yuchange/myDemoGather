package com.allure.mvc.model;

/**
 * 作者：luomin on 16/12/27 08:04
 * 邮箱：asddavid@163.com
 */
public interface UserLoginModel {
    void login(String account,String passWord,UserLoginListener userLoginListener);
}
