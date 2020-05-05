package com.example.atry.simplysalary.globe;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.mob.MobSDK;

import java.util.Iterator;
import java.util.List;


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
        // 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
// 请勿在“=”与appid之间添加任何空字符或者转义符
        SpeechUtility.createUtility(context, SpeechConstant.APPID +"=5ead3268");
        initEMessage();
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

    private void initEMessage() {
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
// 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
// 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);
        options.setAutoAcceptGroupInvitation(false);


        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(context.getPackageName())) {
            Log.e("lw", "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EaseUI.getInstance().init(context,options);
//初始化
//        EMClient.getInstance().init(context, options);
////在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);

    }
   //得到app的名称
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                 Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}
