package com.zkdlu.apiresponsespringbootstarter;

import org.springframework.beans.factory.annotation.Autowired;

public class Test {
    @Autowired
    public TestProperties testProperties;

    public Test() {
        System.out.println("허허");
    }
}
