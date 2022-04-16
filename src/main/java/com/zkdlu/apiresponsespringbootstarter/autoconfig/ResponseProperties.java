package com.zkdlu.apiresponsespringbootstarter.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties("spring.response")
public class ResponseProperties {
    private SuccessProperties success = new SuccessProperties();
    private Map<String, ExceptionProperties> exceptions = new HashMap<>();

    public SuccessProperties getSuccess() {
        return success;
    }

    public void setSuccess(SuccessProperties success) {
        this.success = success;
    }

    public Map<String, ExceptionProperties> getExceptions() {
        return exceptions;
    }

    public void setExceptions(Map<String, ExceptionProperties> exceptions) {
        this.exceptions = exceptions;
    }

    public static class SuccessProperties {
        private Integer code = 200;
        private String msg = "SUCCESS";

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public static class ExceptionProperties {
        private Integer code = 400;
        private String msg = "ERROR";
        private Class<RuntimeException> type;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Class<RuntimeException> getType() {
            return type;
        }

        public void setType(Class<RuntimeException> type) {
            this.type = type;
        }
    }
}
