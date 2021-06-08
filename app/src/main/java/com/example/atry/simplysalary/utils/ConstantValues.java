package com.example.atry.simplysalary.utils;

import android.content.Intent;
import android.content.IntentFilter;

/**
 * 李维:
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
    public static final String LOGIN_SECTION = "login_section";
    public static final String LOGIN_BOSS = "login_boss";
    //统计的日期标识
    public static final String STATICS_LEFT_DATE = "statics_left_date";
    public static final String STATICS_RIGHT_DATE = "statics_right_date";
    //URL
    //服务器域名
    public static final String SERVER_URL= "http://192.168.241.93:8080/SimplySalary";
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

    //排班
    public static final String ARRAGEWORK_MORING = "arragework_moring";
    public static final String ARRAGEWORK_AFTERNOON = "arragework_afternoon";
    public static final String ARRAGEWORK_EVENING = "arragework_evening";
    //请假
    public static final String URL_VACATE = SERVER_URL+"/vacate/Vacate_";

    //排班
    public static final String URL_SCHEDULE = SERVER_URL+"/schedule/Schedule_";
}
