package com.updayup.yuchance.greendaodemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by yuchance on 2018/3/28.
 */

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
    public MySQLiteOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db,
                StudentDao.class);//数据版本变更才会执行
    }
}
