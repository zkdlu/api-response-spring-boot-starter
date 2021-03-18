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
        testConfig.put("one", testProperties.getOne());
        testConfig.put("two", testProperties.getTwo());
        testConfig.put("oneTwo", testProperties.getOneTwo());

        return testConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public Test test(TestConfig testConfig) {
        return new Test(testConfig);
    }
}
