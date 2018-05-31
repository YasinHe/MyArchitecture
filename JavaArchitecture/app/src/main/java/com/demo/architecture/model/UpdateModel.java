package com.demo.architecture.model;

import java.util.List;

/**
 * Created by WJ on 2017/9/21.
 */

public class UpdateModel extends BaseModel{

    /**
     * new_version : 1.0.0
     * size : 10M
     * update_msg : ["1.优化程序修复bug","2.优化程序修复bug"]
     * install : 0
     * download : https://zaocan.myyll.com/Data/zaocan_user_1.0.0.apk
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String new_version;
        private String size;
        private String msg;
        private int install;
        private String download;
        private List<String> update_msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getNew_version() {
            return new_version;
        }

        public void setNew_version(String new_version) {
            this.new_version = new_version;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public int getInstall() {
            return install;
        }

        public void setInstall(int install) {
            this.install = install;
        }

        public String getDownload() {
            return download;
        }

        public void setDownload(String download) {
            this.download = download;
        }

        public List<String> getUpdate_msg() {
            return update_msg;
        }

        public void setUpdate_msg(List<String> update_msg) {
            this.update_msg = update_msg;
        }
    }
}
