package com.umeng.soexample.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
Author:樊卓璞
date:2018/9/24
*/public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "shops.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table shop(id integer primary key autoincrement,shopname text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
