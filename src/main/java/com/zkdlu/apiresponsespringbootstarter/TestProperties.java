package com.zkdlu.apiresponsespringbootstarter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("zkdlu.test")
public class TestProperties {
    private String one = "one-1";
    private String oneTwo = "one-two";

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

    @Override
    public String toString() {
        return one + " : " + oneTwo;
    }
}
