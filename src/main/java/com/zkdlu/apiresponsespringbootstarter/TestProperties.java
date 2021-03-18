package com.zkdlu.apiresponsespringbootstarter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "zkdlu.test")
public class TestProperties {
    private String one;
    private String oneTwo;

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getOneTwo() {
        return oneTwo;
    }

    public void setOneTwo(String oneTwo) {
        this.oneTwo = oneTwo;
    }
}
