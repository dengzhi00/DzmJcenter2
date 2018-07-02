package com.dzm.jar.helper;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dzm on 2018/6/15.
 *
 */

public class SqlHelper {

    private SQLiteOpenHelper sql;

    public SqlHelper(SQLiteOpenHelper sql){
        this.sql = sql;
    }

    public SQLiteDatabase getWritableDatabase(){
        return sql.getWritableDatabase();
    }

    public SQLiteDatabase getReadableDatabase(){
        return sql.getReadableDatabase();
    }

}
