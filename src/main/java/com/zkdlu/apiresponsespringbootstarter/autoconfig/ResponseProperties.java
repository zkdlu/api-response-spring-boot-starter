package com.zkdlu.apiresponsespringbootstarter.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Map;

@ConfigurationProperties("spring.response")
public class ResponseProperties {
    private Map<Exception, ExceptionProperties> exceptions;

    public Map<Exception, ExceptionProperties> getExceptions() {
        return exceptions;
    }

    public void setExceptions(Map<Exception, ExceptionProperties> exceptions) {
        this.exceptions = exceptions;
    }

    public static class ExceptionProperties {
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
}
