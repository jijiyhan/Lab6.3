package com.example.ji.lab6_3;

/**
 * Created by ji on 2018-06-02.
 */

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public MySQLiteOpenHelper(Context context,String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sql="create table student ("+
                "name text, "+
                "studentNo text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldersion,int newVersion){
        String sql="drop table if exists student";
        db.execSQL(sql);
        onCreate(db);
    }


}