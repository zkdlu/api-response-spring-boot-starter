package com.zkdlu.apiresponsespringbootstarter.autoconfig;

import com.zkdlu.apiresponsespringbootstarter.core.advice.ExceptionAdvice;
import com.zkdlu.apiresponsespringbootstarter.core.advice.ResponseAdvice;
import com.zkdlu.apiresponsespringbootstarter.core.config.WebConfig;
import com.zkdlu.apiresponsespringbootstarter.core.service.ResponseService;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ResponseService.class, ExceptionAdvice.class, ResponseAdvice.class, WebConfig.class})
public @interface EnableResponse {

}
