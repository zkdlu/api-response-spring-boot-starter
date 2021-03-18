package com.zkdlu.apiresponsespringbootstarter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Test.class)
@EnableConfigurationProperties(TestProperties.class)
public class TestAutoConfiguration {
    @Autowired
    private TestProperties testProperties;

    @Bean
    @ConditionalOnMissingBean
    public TestConfig testConfig() {
        TestConfig testConfig = new TestConfig();
        String one = testProperties.getOne();
        String two = testProperties.getOneTwo();

        testConfig.put("one", one == null ? System.getProperty("one") : one);
        testConfig.put("oneTwo", two == null ? System.getProperty("one.two") : two);

        return testConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public Test test(TestConfig testConfig) {
        return new Test(testConfig);
    }
}
