package com.zkdlu.apiresponsespringbootstarter.autoconfiguration.config;

import com.zkdlu.apiresponsespringbootstarter.core.service.ResponseService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(value = ResponseService.class)
@EnableConfigurationProperties(ResponseProperties.class)
public class ResponseAutoConfiguration {
    private final ResponseProperties responseProperties;

    public ResponseAutoConfiguration(ResponseProperties responseProperties) {
        this.responseProperties = responseProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ResponseConfig responseConfig() {
        String successCode = responseProperties.getSuccessCode() == null ? "OK"
                : responseProperties.getSuccessCode();
        String successMsg = responseProperties.getSuccessMsg() == null ? "SUCCESS"
                : responseProperties.getSuccessMsg();

        ResponseConfig responseConfig = new ResponseConfig();
        responseConfig.put("success.code", successCode);
        responseConfig.put("success.msg", successMsg);

        return responseConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public ResponseService responseService() {
        return new ResponseService(responseConfig());
    }
}
