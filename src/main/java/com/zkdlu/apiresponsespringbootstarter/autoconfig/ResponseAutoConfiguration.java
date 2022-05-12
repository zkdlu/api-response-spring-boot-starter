package com.zkdlu.apiresponsespringbootstarter.autoconfig;

import com.zkdlu.apiresponsespringbootstarter.core.service.ResponseService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(ResponseService.class)
@EnableConfigurationProperties(ResponseProperties.class)
public class ResponseAutoConfiguration {
}
