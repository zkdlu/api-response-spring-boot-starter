package com.zkdlu.apiresponsespringbootstarter;

public class Test {
    private TestConfig testConfig;

    public Test(TestConfig testConfig) {
        this.testConfig = testConfig;
    }

    public String one() {
        return testConfig.get("one").toString();
    }

    public String two() {
        return testConfig.get("two").toString();
    }

    public String oneTwo() {
        return testConfig.get("oneTwo").toString();
    }
}
