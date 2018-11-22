package com.umeng.soexample.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/*
Author:樊卓璞
date:2018/9/24
*/public class DBDao {

    private SQLiteDatabase db;

    public DBDao(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(String s) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("shopname", s);
        return db.insert("shop", null, contentValues);

    }

    public List<String> select() {
        Cursor cursor = db.rawQuery("select * from shop", null);
        List<String> strings = new ArrayList<>();
        String shopname = null;
        while (cursor.moveToNext()) {
            shopname = cursor.getString(cursor.getColumnIndex("shopname"));
            strings.add(shopname);
        }

        return strings;
    }

    public String[] selectByName(String name) {
//        Cursor cursor = db.rawQuery("select * from shops  where shopname = ?", new String[]{name});
        Cursor cursor = db.query(true, "shop", new String[]{"shopname"}, "shopname" + " = ?", new String[]{name}, null, null, null, null);

        String[] t = {};
        String shopname = null;
        while (cursor.moveToNext()) {
            shopname = cursor.getString(cursor.getColumnIndex("shopname"));
        }
        return t;
    }

    public long delete() {
        return db.delete("shop", null, null);
    }
}
