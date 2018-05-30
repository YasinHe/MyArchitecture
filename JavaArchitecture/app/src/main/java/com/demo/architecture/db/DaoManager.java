package com.demo.architecture.db;

import android.content.Context;

import com.demo.architecture.model.DaoMaster;
import com.demo.architecture.model.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by HeYingXin on 2017/9/27.
 * 目前只做读写，不做加密,数据库升级问题暂不考虑，直接删表升级
 */
public class DaoManager {
    private static final String DB_NAME = "breakfast.db";
    private Context context;
    private volatile static DaoManager manager = new DaoManager();
    private static DaoMaster sDaoMaster;
    private DaoMaster.DevOpenHelper sHelper;
    private static DaoSession sDaoSession;

    public static DaoManager getInstance(){
        return manager;
    }

    public void init(Context context){
        this.context = context;
    }

    private DaoMaster getDaoMaster(){
        if(sDaoMaster == null) {
            sHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
            sDaoMaster = new DaoMaster(sHelper.getWritableDatabase());
        }
        return sDaoMaster;
    }

    DaoSession getDaoSession(){
        if(sDaoSession == null){
            if(sDaoMaster == null){
                sDaoMaster = getDaoMaster();
            }
            sDaoSession = sDaoMaster.newSession();
        }
        return sDaoSession;
    }

    public void setDebug(){
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    public void closeConnection(){
        closeHelper();
        closeDaoSession();
    }

    private void closeHelper(){
        if(sHelper != null){
            sHelper.close();
            sHelper = null;
        }
    }

    private void closeDaoSession(){
        if(sDaoSession != null){
            sDaoSession.clear();
            sDaoSession = null;
        }
    }
}