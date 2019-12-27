package com.example.atry.simplysalary.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtils {
    public static void setBoolean(Context context, String key, boolean value){
        SharedPreferences sp = context.getSharedPreferences(ConstantValues.CONFIG, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).apply();
    }
    public static boolean getBoolean(Context context, String key, boolean defaultvalue){
        SharedPreferences sp = context.getSharedPreferences(ConstantValues.CONFIG, Context.MODE_PRIVATE);
        return sp.getBoolean(key,defaultvalue);
    }
    public static void setString(Context context, String key, String value){
        SharedPreferences sp = context.getSharedPreferences(ConstantValues.CONFIG, Context.MODE_PRIVATE);
        sp.edit().putString(key,value).apply();
    }
    public static String getString(Context context, String key, String defaultvalue){
        SharedPreferences sp = context.getSharedPreferences(ConstantValues.CONFIG, Context.MODE_PRIVATE);
        return sp.getString(key,defaultvalue);
    }
    public static void setInt(Context context, String key, int value){
        SharedPreferences sp = context.getSharedPreferences(ConstantValues.CONFIG, Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).apply();
    }
    public static int getInt(Context context, String key, int defaultvalue){
        SharedPreferences sp = context.getSharedPreferences(ConstantValues.CONFIG, Context.MODE_PRIVATE);
        return sp.getInt(key,defaultvalue);
    }
}
