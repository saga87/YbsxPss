package com.ning.ybsxpss.entity;

import java.io.Serializable;

/**
 * Created by fxn on 2017/9/26.
 */

public class LoginObg implements Serializable{
    private String jsonStr;
    private String msg;
    private boolean success;

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
