package com.example.atry.simplysalary.globe;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.mob.MobSDK;


public class SimplysalaryApplication extends Application {
    private static Context context;

    private static Handler handler;

    private static int Uithreadid;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        Uithreadid = android.os.Process.myTid();
        MobSDK.init(context);
    }

    public static Context getContext(){
            return context;
    }

    public static Handler getHandler(){
        return handler;
    }

    public static int getUithreadid(){
        return  Uithreadid;
    }
}
