package com.demo.architecture.model;

/**
 * Created by HeYingXin on 2017/7/19.
 */
public class BaseModel<T> {

    /**
     * update : OK
     */

    private T t;
    /**
     * succeed : 1
     */
    private StatusBean status;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public static class StatusBean {
        private int succeed;
        private int error_code;
        private String error_desc;

        public int getError_code() {
            return error_code;
        }

        public void setError_code(int error_code) {
            this.error_code = error_code;
        }

        public String getError_desc() {
            return error_desc;
        }

        public void setError_desc(String error_desc) {
            this.error_desc = error_desc;
        }

        public int getSucceed() {
            return succeed;
        }

        public void setSucceed(int succeed) {
            this.succeed = succeed;
        }
    }
}