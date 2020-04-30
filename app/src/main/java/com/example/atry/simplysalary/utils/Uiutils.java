package com.example.atry.simplysalary.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.atry.simplysalary.globe.SimplysalaryApplication;

/**
 *   这是一个UI的工具类，分别用于UI界面处理及得到各个类型的资源
 */


public class Uiutils {
    //得到上下文
    public static Context getContext(){
        return SimplysalaryApplication.getContext();
    }
    //用于线程间通信
    public static Handler gethandler(){
        return SimplysalaryApplication.getHandler();
    }

    //得到主线程的id
    public static int getUithreadId(){
        return SimplysalaryApplication.getUithreadid();
    }
    //得到字符串
    public static String getString(int id){
        return getContext().getResources().getString(id);
    }
    // 得到图片
    public static Drawable getDrawable(int id, Resources.Theme theme){
        return getContext().getResources().getDrawable(id,theme);
    }
    //颜色
    public static int getColor(int id){
        return getContext().getResources().getColor(id);
    }

    //得到图片选择器
    public static ColorStateList getColorStateList(int id){
        return getContext().getResources().getColorStateList(id);
    }

    //判断当前进程是否在主线程
    public static boolean isuithread(){
        int currentthreadid = android.os.Process.myTid();
        if(currentthreadid == getUithreadId()){
            return true;
        }
        return false;
    }
    //运行到分线程
    public static void runOnUiThread(Runnable runnable){
        if(isuithread()){
            runnable.run();
        }else{
            gethandler().post(runnable);
        }


    }
    //弹窗
    public static void toast(String content){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(),content,Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 判断手机号码是否合理
     */
    public static boolean judgePhoneNums(String phoneNums){
        if(isMatchLength(phoneNums,11) && isMobileNo(phoneNums)){
            return true;
        }
        Toast.makeText(getContext(),"手机号输入有误!",Toast.LENGTH_SHORT).show();
        return false;
    }
    /**
     *
     * @param str
     * @param length
     * @return
     *
     * 判断手机号码是否合理,是否为11位
     */
    public static boolean isMatchLength(String str,int length){
        if(str.isEmpty()){
            return false;
        }else{
            return str.length() == length ? true : false;
        }

    }

    /**
     * 验证手机格式
     */

    public static boolean isMobileNo(String mobileNums){
        String telRegex = "[1][3578]\\d{9}";
        if(TextUtils.isEmpty(mobileNums)){
            return false;
        }else{
            return mobileNums.matches(telRegex);
        }
    }
    //判读密码格式
    public static boolean passwordIsMatch(String password){
        if(TextUtils.isEmpty(password)){
            return false;
        }
        if((password.length() >5 ) && (password.length() < 13)){
            return true;
        }
        return false;
    }


}
