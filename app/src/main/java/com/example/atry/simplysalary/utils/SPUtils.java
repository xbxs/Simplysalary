package com.example.atry.simplysalary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 李维: TZZ on 2020-01-06 09:03
 * 邮箱: 3182430026@qq.com
 */
public class SPUtils {
    private static SPUtils instance = new SPUtils();
    private static SharedPreferences mSp;
    //单例
    private SPUtils(){

    }
    //得到单例
    public static SPUtils getInstance(){
        if(mSp == null){
            mSp = Uiutils.getContext().getSharedPreferences(ConstantValues.CONFIG, Context.MODE_PRIVATE);
        }
        return instance;
    }

    //保存
    public void save(String key,Object value){
        if(value instanceof String){
            mSp.edit().putString(key, (String) value).commit();
        }else if(value instanceof Boolean){
            mSp.edit().putBoolean(key, (Boolean) value).commit();
        }else if(value instanceof  Integer){
            mSp.edit().putInt(key, (Integer) value).commit();
        }
    }
    //获取String类型数据
    public String getString(String key,String defValue){
        return mSp.getString(key,defValue);
    }
    //获取Boolean类型数据
    public Boolean getBoolean(String key,boolean defValue){
        return mSp.getBoolean(key,defValue);
    }//获取Int类型数据
    public int getInt(String key,int defValue){
        return mSp.getInt(key,defValue);
    }

}
