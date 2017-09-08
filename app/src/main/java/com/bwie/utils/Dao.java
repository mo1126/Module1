package com.bwie.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Mo on 2017/9/5.
 */

public class Dao {
    public Context context;
    private final MySQLiteOpenHelper help;

    public Dao(Context context) {
        this.context = context;
        help = new MySQLiteOpenHelper(context);
    }

    public void add(String type, String url) {
        SQLiteDatabase writableDatabase = help.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type", type);
        values.put("url", url);
        writableDatabase.insert("news", null, values);
        writableDatabase.close();
    }

    public String select(String type) {
        String url=null;
        SQLiteDatabase writableDatabase = help.getWritableDatabase();
        Cursor query = writableDatabase.query("news", null, "type", new String[]{type}, null, null, null, null);
        while(query.moveToNext()){
            url = query.getString(query.getColumnIndex(type));
        }
        writableDatabase.close();
        return url;
    }
}
