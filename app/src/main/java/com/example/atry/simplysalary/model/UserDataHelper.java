package com.example.atry.simplysalary.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * 李维: TZZ on 2019-11-06 16:17
 * 邮箱: 3182430026@qq.com
 */
public class UserDataHelper extends SQLiteOpenHelper {
    public static final String CREATE_USER = "create table user(phonenumber varchar(11) primary key," +
            "name text," +
            "picture varchar(30)," +
            "flag integer," +
            "bossnumber integer," +
            "department varchar(20))";
    public UserDataHelper(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
