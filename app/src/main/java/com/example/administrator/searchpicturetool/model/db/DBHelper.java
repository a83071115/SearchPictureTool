package com.example.administrator.searchpicturetool.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrator.searchpicturetool.config.MySql;

/**
 * Created by wenhuaijun on 2016/1/31 0031.
 */
public class DBHelper extends SQLiteOpenHelper{
    public DBHelper(Context context) {
        super(context, MySql.DATABASE_NAME, null, MySql.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MySql.CREATE_DOWNLOAD_TABLE);
        db.execSQL(MySql.CREATE_COLLECT_TABLE);
        db.execSQL(MySql.CREATE_RECOMMEND_TABLE);
        db.execSQL(MySql.CREATE_TIP_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MySql.CREATE_RECOMMEND_TABLE);
        db.execSQL(MySql.CREATE_TIP_TABLE);
    }
}
