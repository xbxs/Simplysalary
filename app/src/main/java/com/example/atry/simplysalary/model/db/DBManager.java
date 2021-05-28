package com.example.atry.simplysalary.model.db;

import android.content.Context;

import com.example.atry.simplysalary.model.dao.ContactTableDao;
import com.example.atry.simplysalary.model.dao.InviteTableDao;

/**
 * 李维:
 * 邮箱: 3182430026@qq.com
 */
public class DBManager {
    private final ContactsDBHelper mDBHelper;
    private final ContactTableDao mContactTableDao;
    private final InviteTableDao mInviteTableDao;


    public DBManager(Context context, String name){
        //创建数据库
        mDBHelper = new ContactsDBHelper(context,name);
        //创建数据中的两张表
        mContactTableDao = new ContactTableDao(mDBHelper);
        mInviteTableDao = new InviteTableDao(mDBHelper);
    }

    //得到联系人表操作类
    public ContactTableDao getContactTableDao(){
        return mContactTableDao;
    }

    //得到邀请信息表的操作类
    public InviteTableDao getInviteTableDao(){
        return mInviteTableDao;
    }

    //关闭数据库
    public void close(){
        mDBHelper.close();
    }
}
