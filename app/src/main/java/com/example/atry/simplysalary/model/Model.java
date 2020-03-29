package com.example.atry.simplysalary.model;

import android.content.Context;

import com.example.atry.simplysalary.model.bean.User;
import com.example.atry.simplysalary.model.dao.UserAccountDao;
import com.example.atry.simplysalary.model.db.DBManager;
import com.example.atry.simplysalary.utils.Uiutils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 李维: TZZ on 2019-12-27 20:55
 * 邮箱: 3182430026@qq.com
 */
public class Model {
    //创建对象
    private static Model mModel = new Model();
    private Context mContext;
    //线程池
    private ExecutorService mExecutorService = Executors.newCachedThreadPool();
    //数据库操作类
    private UserAccountDao mUserAccountDao;
    private DBManager mDbManager;

    //私有化构造方法
    private Model(){
        init(Uiutils.getContext());
    }
    //获取单例模式
    public static Model getInstance(){

        return mModel;
    }
    //初始化方法
    public void init(Context context){
        mContext = context;
        mUserAccountDao = new UserAccountDao(context);
        EventListener eventListener = new EventListener(mContext);
    }

    //获取全局的线程池对象
    public ExecutorService getGlobalThreadPool(){
        return mExecutorService;
    }

    //用户登录成功后的处理方法
    public void loginSuccess(User user){
        if(user == null) {
            return;
        }
        if(mDbManager != null){
            mDbManager.close();
        }
        mDbManager = new DBManager(mContext,user.getPhonenumber());

    }

    //得到联系人两个表的管理者
    public DBManager getDbManager(){
        return mDbManager;
    }

    //获取用户账号数据库的操作类对象
    public UserAccountDao getUserAccountDao(){
        return mUserAccountDao;
    }
}
