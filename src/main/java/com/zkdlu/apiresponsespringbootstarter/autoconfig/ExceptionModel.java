package com.zkdlu.apiresponsespringbootstarter.autoconfig;

public class ExceptionModel {
    private String code;
    private String msg;
    private Class<Exception> type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Class<Exception> getType() {
        return type;
    }

    public void setType(Class<Exception> type) {
        this.type = type;
    }
}
