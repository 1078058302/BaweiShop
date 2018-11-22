package com.umeng.soexample.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.umeng.soexample.GreenDao.DaoMaster;
import com.umeng.soexample.GreenDao.DataBeanDao;
import com.umeng.soexample.model.DataBean;

import java.util.List;

public class DataBeanJsonUtils {
    private DataBeanDao dataBeanDao;

    private DataBeanJsonUtils() {
    }

    private static DataBeanJsonUtils dataBeanJsonUtils;

    public static DataBeanJsonUtils getNewsBeanJsonUtils() {
        if (dataBeanJsonUtils == null) {
            dataBeanJsonUtils = new DataBeanJsonUtils();
        }
        return dataBeanJsonUtils;
    }

    //初始化
    public void init(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "data");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster mDaoMaster = new DaoMaster(db);
        dataBeanDao = mDaoMaster.newSession().getDataBeanDao();
    }

    //增
    public void insert(DataBean dataBean) {
        dataBeanDao.insert(dataBean);
    }

    //查询全部
    public List<DataBean> queryAll() {
        return dataBeanDao.loadAll();
    }

    //查询单个
    public DataBean query(String key) {
        return dataBeanDao.load(Long.parseLong(key));
    }

    //删除全部
    public void deleteAll() {
        dataBeanDao.deleteAll();
    }

    //删除单个
    public void delete(String key) {
        dataBeanDao.deleteByKey(Long.parseLong(key));
    }

    //改
    public void update(DataBean newsBean) {
        dataBeanDao.update(newsBean);
    }
}
