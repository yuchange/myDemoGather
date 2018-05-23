package com.updayup.yuchance.greendaodemo;

import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by yuchance on 2018/3/28.
 */

public class GreenDaoUtils {
//    private MySQLiteOpenHelper mHelper;
//    private SQLiteDatabase db;
//    private DaoMaster mDaoMaster;
//    private DaoSession mDaoSession;
//
//    private static class GreenDaoUtilsHolder {
//        private static final GreenDaoUtils INSTANCE = new GreenDaoUtils();
//    }
//
//    private GreenDaoUtils() {
//    }
//
//    public static GreenDaoUtils getSingleton() {
//        return GreenDaoUtilsHolder.INSTANCE;
//    }
//
//    private void initGreenDao() {
//        mHelper = new MySQLiteOpenHelper(App.getInstance(),
//                "test-db", null);
//        db = mHelper.getWritableDatabase();
//        mDaoMaster = new DaoMaster(db);
//        mDaoSession = mDaoMaster.newSession();
//    }
//
//    public DaoSession getDaoSession() {
//        if (mDaoMaster == null) {
//            initGreenDao();
//        }
//        return mDaoSession;
//    }
//    /**
//     * 插入一条记录
//     *
//     * @param user
//     */
//    public void insertUser(Student user) {
//        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        StudentDao userDao = daoSession.getStudentDao();
//        userDao.insert(user);
//    }
//
//
//    /**
//     * 插入用户集合
//     *
//     * @param users
//     */
//    public void insertUserList(List<Student> users) {
//        if (users == null || users.isEmpty()) {
//            return;
//        }
//        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        StudentDao userDao = daoSession.getStudentDao();
//        userDao.insertInTx(users);
//    }
//
//
//    /**
//     * 删除一条记录
//     *
//     * @param user
//     */
//    public void deleteUser(Student user) {
//        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        StudentDao userDao = daoSession.getStudentDao();
//        userDao.delete(user);
//    }
//
//    /**
//     * 更新一条记录
//     *
//     * @param user
//     */
//    public void updateUser(Student user) {
//        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        StudentDao userDao = daoSession.getStudentDao();
//        userDao.update(user);
//    }
//
//    /**
//     * 查询用户列表
//     */
//    public List<Student> queryUserList() {
//        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        StudentDao userDao = daoSession.getStudentDao();
//        QueryBuilder<Student> qb = userDao.queryBuilder();
//        List<Student> list = qb.list();
//        return list;
//    }
//
//    /**
//     * 查询用户列表
//     */
//    public List<Student> queryUserList(int age) {
//        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        StudentDao userDao = daoSession.getStudentDao();
//        QueryBuilder<Student> qb = userDao.queryBuilder();
//        qb.where(StudentDao.Properties.StuId.gt(age)).orderAsc(StudentDao.Properties.StuId);
//        List<Student> list = qb.list();
//        return list;
//    }
}
