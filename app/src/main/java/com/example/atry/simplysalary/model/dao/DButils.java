package com.example.atry.simplysalary.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.atry.simplysalary.model.db.UserDataHelper;
import com.example.atry.simplysalary.model.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 李维:
 * 邮箱: 3182430026@qq.com
 */
public class DButils {
    /**
     * 当对数据库进行增、删、改时的数据库
     * @param context
     * @return
     */
    private UserDataHelper mHelper;
    private SQLiteDatabase mDatabase;

    /**
     * 初始化数据库
     * @param context
     */
    public DButils(Context context){
        mHelper = new UserDataHelper(context);
        mDatabase = mHelper.getWritableDatabase();
    }

    /**
     * 插入数据
     * @param contentValues
     * @return
     */
    public long insertUser(ContentValues contentValues){
        long count = mDatabase.insert("user",null,contentValues);
        mDatabase.close();
        mHelper.close();
        return count;
    }

    /**
     * 删除数据
     * @param phonenumber
     * @return
     */
    public int deleteUser(String phonenumber){
        int count = mDatabase.delete("user","phonenumber = ?",new String[]{phonenumber});
        mDatabase.close();
        mHelper.close();
        return count;
    }

    /**
     *修改数据
     * @param phonenumber
     * @param contentValues
     * @return
     */
    public int updateUser(String phonenumber,ContentValues contentValues){
        int count = mDatabase.update("user",contentValues,"phonenumber = ?",new String[]{phonenumber});
        mDatabase.close();
        mHelper.close();
        return count;
    }


    public User queryUser(String phonenumber){
        Cursor cursor = mDatabase.query("user",null,"phonenumber = ?",new String[]{phonenumber},null,null,null);
        User user = null;
        if(cursor.moveToFirst()){
            String userphonenumber = cursor.getString(cursor.getColumnIndex("phonenumber"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String picture = cursor.getString(cursor.getColumnIndex("picture"));
            int flag = cursor.getInt(cursor.getColumnIndex("flag"));
            int bossnumber = cursor.getInt(cursor.getColumnIndex(""));
            String department = cursor.getString(cursor.getColumnIndex("department"));
//            user = new User(userphonenumber,name,picture,flag,);
        }
        cursor.close();

        return user;
    }

    public List<User> queryDepartmentUser(String department){
        Cursor cursor = mDatabase.query("user",null,"department = ?",new String[]{department},null,null,null);
        List<User> list = null;
        if(cursor.moveToFirst()){
            list = new ArrayList<>();
            do {

                String userphonenumber = cursor.getString(cursor.getColumnIndex("phonenumber"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String picture = cursor.getString(cursor.getColumnIndex("picture"));
                int flag = cursor.getInt(cursor.getColumnIndex("flag"));
                int bossnumber = cursor.getInt(cursor.getColumnIndex("bossnumber"));
//                User user = new User(userphonenumber, name, picture, flag, bossnumber, department);
//                list.add(user);
            }while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }





}
