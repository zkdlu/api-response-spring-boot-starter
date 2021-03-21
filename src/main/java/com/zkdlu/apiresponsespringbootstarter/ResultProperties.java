package com.zkdlu.apiresponsespringbootstarter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.response")
public class ResultProperties {
    private int code = 0;
    private String msg = "테스트";

    public Class<Exception> getType() {
        return type;
    }

    public void setType(Class<Exception> type) {
        this.type = type;
    }

    private Class<Exception> type = Exception.class;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
