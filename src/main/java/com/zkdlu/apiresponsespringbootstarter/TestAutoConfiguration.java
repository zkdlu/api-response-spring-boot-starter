package com.zkdlu.apiresponsespringbootstarter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Test.class)
@EnableConfigurationProperties(TestProperties.class)
public class TestAutoConfiguration {


}
