package com.zkdlu.apiresponsespringbootstarter.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("spring.response")
public class ResponseProperties {
    private List<ExceptionModel> exceptions;

    public List<ExceptionModel> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<ExceptionModel> exceptions) {
        this.exceptions = exceptions;
    }
}
