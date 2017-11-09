package com.zwj.zhuangwjutils.greendao;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * Created by zwj on 2017/11/6.
 */

public class DbManager {

    // 是否加密
    public static final boolean ENCRYPTED = true;

    public static final String DB_MY = "my.db";
    public static final String DB_MY_PASSWORD = "myPassword";

    private static DbManager mDbManager;
    private static DaoMaster.DevOpenHelper mDevOpenHelper;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

//    private Context mContext;

    private DbManager(Context context, String dbName, String passwprd) {
        // 初始化数据库信息
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, dbName);
        getDaoMaster(context, dbName, passwprd);
        getDaoSession(context, dbName, passwprd);
    }

    public static DbManager getInstance(Context context, String dbName, String passwprd) {
        if (null == mDbManager) {
            synchronized (DbManager.class) {
                if (null == mDbManager) {
                    mDbManager = new DbManager(context, dbName, passwprd);
                }
            }
        }
        return mDbManager;
    }

    /**
     * 获取可读数据库
     *
     * @param context
     * @return
     */
    public static Database getReadableDatabase(Context context, String dbName, String passwprd) {
        if (null == mDevOpenHelper) {
            getInstance(context, dbName, passwprd);
        }
        if (ENCRYPTED) {//加密
            return mDevOpenHelper.getEncryptedReadableDb(passwprd);
        } else {
            return mDevOpenHelper.getReadableDb();
        }
    }

    /**
     * 获取可写数据库
     *
     * @param context
     * @param dbName
     * @param passwprd
     * @return
     */
    public static Database getWritableDatabase(Context context, String dbName, String passwprd) {
        if (null == mDevOpenHelper) {
            getInstance(context, dbName, passwprd);
        }
        if (ENCRYPTED) {//加密
            return mDevOpenHelper.getEncryptedWritableDb(passwprd);
        } else {
            return mDevOpenHelper.getWritableDb();
        }
    }

    /**
     * 获取DaoMaster
     *
     * @param context
     * @param dbName
     * @param passwprd
     * @return
     */
    public static DaoMaster getDaoMaster(Context context, String dbName, String passwprd) {
        if (null == mDaoMaster) {
            synchronized (DbManager.class) {
                if (null == mDaoMaster) {
                    mDaoMaster = new DaoMaster(getWritableDatabase(context, dbName, passwprd));
                }
            }
        }
        return mDaoMaster;
    }

    /**
     * 获取DaoSession
     *
     * @param context
     * @param dbName
     * @param passwprd
     * @return
     */
    public static DaoSession getDaoSession(Context context, String dbName, String passwprd) {
        if (null == mDaoSession) {
            synchronized (DbManager.class) {
//                mDaoSession = getDaoMaster(context,dbName,passwprd).newSession();
                mDaoSession = getDaoMaster(context, dbName, passwprd).newDevSession(context, dbName);
            }
        }

        return mDaoSession;
    }
}