package com.updayup.yuchance.greendaodemo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.updayup.yuchance.greendaodemo.DaoMaster;
import com.updayup.yuchance.greendaodemo.DaoSession;

/**
 * Created by yuchance on 2018/3/28.
 */

public class App extends Application{

    private StudentDao dao;
    private static App mApp;
    private static DaoSession daoSession;
    private SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
//        GreenDaoUtils.getSingleton();
        initGreenDao();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"student.db");
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

    }


//    public static App getInstance(){
//        if (mApp == null){
//            synchronized (App.class){
//                mApp = new App();
//            }
//        }
//        return mApp;
//    }

    public static DaoSession getDaoSession(){


        return daoSession;

    }






}
