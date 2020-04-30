package com.example.atry.simplysalary.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.atry.simplysalary.model.bean.User;
import com.example.atry.simplysalary.model.db.ContactsDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 李维: TZZ on 2020-01-06 13:35
 * 邮箱: 3182430026@qq.com
 */
public class ContactTableDao {
    private ContactsDBHelper mContactsDBHelper;

    public ContactTableDao(ContactsDBHelper dbHelper){
        mContactsDBHelper = dbHelper;
    }

    //获取所有联系人
    public List<User> getContacts(){
        SQLiteDatabase db = mContactsDBHelper.getReadableDatabase();
        String sql = "select * from " + ContactTable.TAB_NAME+" where "+ContactTable.COL_IS_CONTACT+"=1";
        Cursor cursor = db.rawQuery(sql,null);
        List<User> list = new ArrayList<>();
        while (cursor.moveToNext()){
            User user = new User();
            user.setPhonenumber(cursor.getString(cursor.getColumnIndex(ContactTable.COL_PHONEID)));
            user.setName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NAME)));
            user.setPicture(cursor.getString(cursor.getColumnIndex(ContactTable.COL_PICTURE)));
            user.setFlag(cursor.getInt(cursor.getColumnIndex(ContactTable.COL_FLAG)));
         //   user.setBossnumber(cursor.getInt(cursor.getColumnIndex(ContactTable.COL_BOSSNUMBER)));
            user.setDepartment(cursor.getString(cursor.getColumnIndex(ContactTable.COL_DEPARTMENT)));
            list.add(user);

        }
        //关闭资源
        cursor.close();
        //返回数据
        return list;
    }

    //通过环信id 获取联系人单个信息
    public User getContactByHx(String hxid){
        if(hxid == null)
            return null;
        SQLiteDatabase db = mContactsDBHelper.getReadableDatabase();
        //执行sql语句
        String sql = "select * from "+ContactTable.TAB_NAME+" where "+ContactTable.COL_PHONEID+"=?";
        Cursor cursor = db.rawQuery(sql,new String[]{hxid});
        User user = null;
        if(cursor.moveToNext()){
            user = new User();
            user.setPhonenumber(cursor.getString(cursor.getColumnIndex(ContactTable.COL_PHONEID)));
            user.setName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NAME)));
            user.setPicture(cursor.getString(cursor.getColumnIndex(ContactTable.COL_PICTURE)));
            user.setFlag(cursor.getInt(cursor.getColumnIndex(ContactTable.COL_FLAG)));
           // user.setBossnumber(cursor.getInt(cursor.getColumnIndex(ContactTable.COL_BOSSNUMBER)));
            user.setDepartment(cursor.getString(cursor.getColumnIndex(ContactTable.COL_DEPARTMENT)));
        }

        //关闭资源
        cursor.close();
        //返回数据
        return user;
    }

    //通过环信id 获取用户联系人信息
    public List<User> getContactsByHx(List<String> hxids){
        if(hxids == null || hxids.size() <= 0){
            return null;
        }
        List<User> mlist = new ArrayList<>();
        //遍历hxids来查找个人信息
        for(String hxid : hxids){

            User user = getContactByHx(hxid);
            mlist.add(user);
        }

        return mlist;

    }

    //保存单个联系人
    public void saveContact(User user,boolean isMyContact){
        if(user == null){
            return;
        }

        //获取数据库连接
        SQLiteDatabase database = mContactsDBHelper.getWritableDatabase();
        //执行保存语句
        ContentValues values = new ContentValues();
        values.put(ContactTable.COL_PHONEID,user.getPhonenumber());
        values.put(ContactTable.COL_NAME,user.getName());
        values.put(ContactTable.COL_PICTURE,user.getPicture());
        values.put(ContactTable.COL_FLAG,user.getFlag());
        //values.put(ContactTable.COL_BOSSNUMBER,user.getBossnumber());
        values.put(ContactTable.COL_DEPARTMENT,user.getDepartment());
        values.put(ContactTable.COL_IS_CONTACT,isMyContact ? 1 : 0);

        database.replace(ContactTable.TAB_NAME,null,values);
    }

    //保存多个联系人信息
    public void saveContacts(List<User> users,boolean isMyContact){
        if(users == null || users.size() <= 0){
            return;
        }
        for(User user : users){
            saveContact(user,isMyContact);
        }

    }

    //根据环信id删除个人信息
    public void deleteContactByHxId(String hxid){
        //得到数据库连接
        SQLiteDatabase database = mContactsDBHelper.getWritableDatabase();
        //执行删除语句

        database.delete(ContactTable.TAB_NAME,ContactTable.COL_PHONEID+"=?",new String[]{hxid});
    }

}
