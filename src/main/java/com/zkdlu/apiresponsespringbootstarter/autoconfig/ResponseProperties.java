package com.zkdlu.apiresponsespringbootstarter.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties("spring.response")
public class ResponseProperties {
    private List<ExceptionModel> exceptions = new ArrayList<>();

    @Bean
    @ConfigurationProperties("exceptions")
    public List<ExceptionModel> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<ExceptionModel> exceptions) {
        this.exceptions = exceptions;
    }
}
