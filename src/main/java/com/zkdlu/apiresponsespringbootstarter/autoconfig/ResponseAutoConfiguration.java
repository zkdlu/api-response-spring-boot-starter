package com.zkdlu.apiresponsespringbootstarter.autoconfig;

import com.zkdlu.apiresponsespringbootstarter.core.advice.ExceptionAdvice;
import com.zkdlu.apiresponsespringbootstarter.core.advice.ResponseAdvice;
import com.zkdlu.apiresponsespringbootstarter.core.config.WebConfig;
import com.zkdlu.apiresponsespringbootstarter.core.service.ResponseService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnClass(ResponseService.class)
@Import({ExceptionAdvice.class, ResponseAdvice.class, WebConfig.class})
@EnableConfigurationProperties(ResponseProperties.class)
public class ResponseAutoConfiguration {
}
