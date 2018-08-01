package com.example.zhangweikang.book_search.Shop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrderDBHelper extends SQLiteOpenHelper{

    private static final int DB_VERSION = 1;
    private static final String DB_NAME="add.db";
    public static final String TABLE_NAME = "Orders";
    private static OrderDBHelper instance;
    public OrderDBHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table if not exists "+ TABLE_NAME +"(ID text ,Name text,Tel text,Addr text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql= "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public static OrderDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new OrderDBHelper(context);
        }

        return instance;    }
}
