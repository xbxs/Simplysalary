package com.example.atry.simplysalary.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.atry.simplysalary.model.bean.User;
import com.example.atry.simplysalary.model.db.UserDataHelper;

/**
 * 李维:
 * 邮箱: 3182430026@qq.com
 */
public class UserAccountDao {
    private final UserDataHelper mUserDataHelper;
    public UserAccountDao(Context context){
        mUserDataHelper = new UserDataHelper(context);
    }

    //添加用户到数据库
    public long addUser(User user){
        //获取数据库对象
        SQLiteDatabase db = mUserDataHelper.getWritableDatabase();

        //执行添加操作
        ContentValues values = new ContentValues();
        values.put(UserAccountTable.COL_PHONEID,user.getPhonenumber());
        values.put(UserAccountTable.COL_NAME,user.getName());
        values.put(UserAccountTable.COL_PICTURE,user.getPicture());
        values.put(UserAccountTable.COL_FLAG,user.getFlag());
        values.put(UserAccountTable.COL_BAS,user.getU_bas());
        values.put(UserAccountTable.COL_WAGE,user.getU_wage());
        values.put(UserAccountTable.COL_DEPARTMENT,user.getDepartment());

        long result =  db.replace(UserAccountTable.TAB_NAME,null,values);
        return result;
    }

    //根据id获取所有用户信息
    public User getUser(String id){
        SQLiteDatabase database = mUserDataHelper.getReadableDatabase();
        String sql = "select * from "+UserAccountTable.TAB_NAME+" where "
                +UserAccountTable.COL_PHONEID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{id});
        User user = null;
        if(cursor.moveToNext()){
            user = new User();
            user.setPhonenumber(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_PHONEID)));
            user.setName(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NAME)));
            user.setPicture(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_PICTURE)));
            user.setFlag(cursor.getInt(cursor.getColumnIndex(UserAccountTable.COL_FLAG)));
            user.setU_bas(cursor.getInt(cursor.getColumnIndex(UserAccountTable.COL_BAS)));
            user.setDepartment(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_DEPARTMENT)));
        }

        cursor.close();
        return user;
    }
}
