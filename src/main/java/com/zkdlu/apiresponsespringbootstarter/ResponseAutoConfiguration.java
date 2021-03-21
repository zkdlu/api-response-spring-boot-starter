package com.zkdlu.apiresponsespringbootstarter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnClass(Test.class)
@Import({ResponseAdvice.class, WebConfig.class})
@EnableConfigurationProperties(ResultProperties.class)
public class ResponseAutoConfiguration {

}
