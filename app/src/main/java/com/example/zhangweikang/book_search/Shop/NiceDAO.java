package com.example.zhangweikang.book_search.Shop;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class NiceDAO {
    private static OrderDBHelper helper;
    private static SQLiteDatabase db;

    private NiceDAO(Context context){
        helper = OrderDBHelper.getInstance(context);
    }

    private ArrayList<Info_care> getAll(){
        ArrayList<Info_care> all_lst = new ArrayList<Info_care>();
        Cursor cursor=db.rawQuery("select  *  from "+OrderDBHelper.TABLE_NAME ,null);
        return all_lst;
    }
}
