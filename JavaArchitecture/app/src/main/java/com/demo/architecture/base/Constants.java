package com.demo.architecture.base;

/**
 * Created by HeYingXin on 2017/6/23.
 */
public class Constants {

    public static class Data{
    }

    public static class Server {
        public static boolean isUnitTest = false;//是否是在做单元测试
        public static final String BaseServer = "https://video.myyll.com/";
    }

    public static class UserConfig {
        public static final String USER_ID = "user_id";
        public static final String TOKEN = "token";
    }

    public static class SharedPreferences{
        //上次用户选择的支付方式，下次就默认给他选上次的支付方式  0支付宝  1微信
        public static final String LAST_PAY_WAY = "user_pay_way";
    }

    public static class SystemConfig{
        public static final String APP_NAME = "Xiaou";
        public static boolean ISACTIVE = false;//是否在前台运行
    }

    public static class EventBus{
        /**
         * 活动
         */
        public static final String HD_INFORMATION= "hd_information";
    }

    public static class IntentRequest{
        //首页搜索地址
        public static final int Search_MAP = 1012;
    }

    public static class Http{
        /**
         * 上传carsh
         */
        public static final String PUSH_CARSH = Server.BaseServer +"A/";

    }
}