package com.example.atry.simplysalary.utils;

import android.content.Intent;
import android.content.IntentFilter;

/**
 * 李维: TZZ on 2019-11-06 16:33
 * 邮箱: 3182430026@qq.com
 */
public class ConstantValues {
    /**
     * sp文件的名称
     */

    public static final String CONFIG = "config";
    //是否第一次打开app
    public static final String IS_FIRSTENTER = "is_firstenter";
    //登录者的身份
    public static final String LOGIN_IDENTITY = "login_identity";
    public static final String CONTACT_CHANGED = "contact_changed";
    public static final String CONTACT_INVITE_CHANGED = "contact_invite_changed";
    public static final String IS_NEW_INVITE = "is_new_invite";
    public static final String SECTION_CREATE = "section_create";
    public static final String  SECTION_CHANGED = "section_changed";
    public static final String SECTION_ID = "section_id";
    public static final String SECTION_NAME = "section_name";

    //URL
    //服务器域名
    public static final String SERVER_URL= "http://192.168.43.24:8080/SimplySalary";
    //登录的url
    public static final String URL_Login = SERVER_URL+"/users/Users_login";
    //注册的url
    public static final String URL_Register = SERVER_URL+"/users/Users_add";
    //对User操作的url
    public static final String URL_USER = SERVER_URL+"/users/Users_";
    //注册成功的标识
    public static final String SUCCESSCODE_REGISTER = "0";

    //工资的url
    public static final String URL_SALARY = SERVER_URL+"/salary/Salary_";
}
