package com.example.atry.simplysalary.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.atry.simplysalary.model.dao.ContactTable;
import com.example.atry.simplysalary.model.dao.InviteTable;

/**
 * 李维:
 * 邮箱: 3182430026@qq.com
 */
public class ContactsDBHelper extends SQLiteOpenHelper {
    public ContactsDBHelper(@Nullable Context context, @Nullable String name) {
        super(context, name, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建联系人表
        sqLiteDatabase.execSQL(ContactTable.CREATE_USER);
        //创建邀请表
        sqLiteDatabase.execSQL(InviteTable.CREATE_INVITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
