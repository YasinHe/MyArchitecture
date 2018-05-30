package com.demo.architecture.db;

import android.content.Context;

/**
 * Created by HeYingXin on 2017/9/27.
 * 数据库操作实例
 */
public class DBImpl implements DbApption{

    private static final String TAG = DBImpl.class.getSimpleName();
    private DaoManager mManager;

    public DBImpl(Context context){
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

}
