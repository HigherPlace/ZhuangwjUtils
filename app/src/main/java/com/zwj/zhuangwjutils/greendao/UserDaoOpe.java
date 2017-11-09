package com.zwj.zhuangwjutils.greendao;

import android.content.Context;

import com.zwj.zhuangwjutils.bean.User;
import com.zwj.zwjutils.CommonUtil;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by zwj on 2017/11/6.
 */

public class UserDaoOpe {

    /**
     * 添加数据至数据库
     */
    public static long insertData(Context context, User user) {
        return DbManager.getDaoSession(context, DbManager.DB_MY, DbManager.DB_MY_PASSWORD).getUserDao().insert(user);
    }


    /**
     * 将数据实体通过事务添加至数据库
     */
    public static void insertData(Context context, List<User> list) {
        if (!CommonUtil.isValidList(list)) {
            return;
        }
        DbManager.getDaoSession(context, DbManager.DB_MY, DbManager.DB_MY_PASSWORD).getUserDao().insertInTx(list);
    }

    /**
     * 添加数据至数据库，如果存在，将原来的数据覆盖
     * 内部代码判断了如果存在就update(entity);不存在就insert(entity)；
     */
    public static void saveData(Context context, User user) {
        DbManager.getDaoSession(context, DbManager.DB_MY, DbManager.DB_MY_PASSWORD).getUserDao().save(user);
    }

    /**
     * 删除数据至数据库
     */
    public static void deleteData(Context context, User user) {
        DbManager.getDaoSession(context, DbManager.DB_MY, DbManager.DB_MY_PASSWORD).getUserDao().delete(user);
    }

    /**
     * 根据id删除数据至数据库
     */
    public static void deleteByKeyData(Context context, String id) {
        DbManager.getDaoSession(context, DbManager.DB_MY, DbManager.DB_MY_PASSWORD).getUserDao().deleteByKey(id);
    }

    /**
     * 删除全部数据
     */
    public static void deleteAllData(Context context) {
        DbManager.getDaoSession(context, DbManager.DB_MY, DbManager.DB_MY_PASSWORD).getUserDao().deleteAll();
    }

    /**
     * 更新数据库
     */
    public static void updateData(Context context, User user) {
        DbManager.getDaoSession(context, DbManager.DB_MY, DbManager.DB_MY_PASSWORD).getUserDao().update(user);
    }

    /**
     * 查询所有数据
     */
    public static List<User> queryAll(Context context) {
        QueryBuilder<User> builder = DbManager.getDaoSession(context, DbManager.DB_MY, DbManager.DB_MY_PASSWORD).getUserDao().queryBuilder();
        return builder.build().list();
    }

    /**
     * 根据id，其他的字段类似
     */
    public static List<User> queryForId(Context context, String id) {
        QueryBuilder<User> builder = DbManager.getDaoSession(context, DbManager.DB_MY, DbManager.DB_MY_PASSWORD).getUserDao().queryBuilder();
        /**
         * 返回当前id的数据集合,当然where(这里面可以有多组，做为条件);
         * 这里build.list()；与where(StudentDao.Properties.Id.eq(id)).list()结果是一样的；
         * 在QueryBuilder类中list()方法return build().list();
         */
        // Query<Student> build = builder.where(StudentDao.Properties.Id.eq(id)).build();
        // List<Student> list = build.list();
        return builder.where(UserDao.Properties.Id.eq(id)).list();
    }
}
