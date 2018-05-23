package com.updayup.yuchance.greendaodemo;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private StudentDao mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mDao = App.getInstance().getDao();
//        App app = (App) getApplication();
        DaoSession daoSession = App.getDaoSession();
        mDao = daoSession.getStudentDao();
        Student student = new Student(null,"001","hedabao","男","100");
//        long end = mDao.insert(student);
        String msg = "";
//        if (end>0){
//            msg = "新增成功";
//        }else {
//            msg = "新增失败";
//        }
        mDao.insert(student);
        mDao.insert(new Student(null,"002","003","004","005"));
        Toast.makeText(this,"新增成功",Toast.LENGTH_SHORT).show();



    }
}
