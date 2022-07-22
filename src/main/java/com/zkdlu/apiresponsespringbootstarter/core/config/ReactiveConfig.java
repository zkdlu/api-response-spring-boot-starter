package com.zkdlu.apiresponsespringbootstarter.core.config;

import com.zkdlu.apiresponsespringbootstarter.autoconfig.ResponseProperties;
import com.zkdlu.apiresponsespringbootstarter.core.advice.ReactiveResponseAdvice;
import com.zkdlu.apiresponsespringbootstarter.core.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.config.DelegatingWebFluxConfiguration;
import org.springframework.web.reactive.config.WebFluxConfigurationSupport;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;

@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnClass(WebFluxConfigurer.class)
@ConditionalOnMissingBean({ WebFluxConfigurationSupport.class })
@AutoConfigureAfter({ ReactiveWebServerFactoryAutoConfiguration.class, CodecsAutoConfiguration.class,
        ValidationAutoConfiguration.class })
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
public class ReactiveConfig {
    @Configuration(proxyBeanMethods = false)
    @EnableConfigurationProperties({ WebFluxProperties.class })
    @Import({ EnableWebFluxConfiguration.class })
    public static class WebFluxConfig implements WebFluxConfigurer {
        @Override
        public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        }
    }

    @Configuration(proxyBeanMethods = false)
    public static class EnableWebFluxConfiguration extends DelegatingWebFluxConfiguration {
    }

    @Autowired
    private ResponseService responseService;

    @Autowired
    private ResponseProperties responseProperties;

    @Bean
    public ResponseBodyResultHandler responseBodyResultHandler(
            ServerCodecConfigurer serverCodecConfigurer,
            RequestedContentTypeResolver contentTypeResolver) {
        return new ReactiveResponseAdvice(serverCodecConfigurer.getWriters(),
                contentTypeResolver, responseService, responseProperties);
    }
}
