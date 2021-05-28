package com.example.atry.simplysalary.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import static com.example.atry.simplysalary.model.dao.UserAccountTable.CREATE_USER;

/**
 * 李维:
 * 邮箱: 3182430026@qq.com
 */
public class UserDataHelper extends SQLiteOpenHelper {

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
