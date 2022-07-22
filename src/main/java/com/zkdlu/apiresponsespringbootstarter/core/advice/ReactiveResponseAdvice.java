package com.zkdlu.apiresponsespringbootstarter.core.advice;

import com.zkdlu.apiresponsespringbootstarter.autoconfig.ResponseProperties;
import com.zkdlu.apiresponsespringbootstarter.core.model.CommonResult;
import com.zkdlu.apiresponsespringbootstarter.core.service.ResponseService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.MethodParameter;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.PathContainer;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ReactiveResponseAdvice extends ResponseBodyResultHandler {
    private final List<PathPattern> whitelist = Arrays.asList(
            new PathPatternParser().parse("/v*/api-docs"),
            new PathPatternParser().parse("/swagger-resources/**"),
            new PathPatternParser().parse("/swagger-ui.html"),
            new PathPatternParser().parse("/webjars/**"),
            new PathPatternParser().parse("/swagger/**"));
    private static MethodParameter param;

    private final ResponseService responseService;
    private final ResponseProperties responseProperties;

    static {
        try {
            param = new MethodParameter(ReactiveResponseAdvice.class
                    .getDeclaredMethod("methodForParams"), -1);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public ReactiveResponseAdvice(List<HttpMessageWriter<?>> writers, RequestedContentTypeResolver resolver, ResponseService responseService, ResponseProperties responseProperties) {
        super(writers, resolver);
        this.responseService = responseService;
        this.responseProperties = responseProperties;
    }

    @Override
    public boolean supports(HandlerResult result) {
        return true;
    }

    private static Object methodForParams() {
        return null;
    }

    @Override
    public Mono<Void> handleResult(ServerWebExchange exchange, HandlerResult result) {
        if (whitelist.stream().anyMatch(pathPattern -> pathPattern.matches(PathContainer.parsePath(exchange.getRequest().getURI().getPath())))
                || result.getReturnValue() instanceof CommonResult) {
            return writeBody(result.getReturnValue(), param, exchange);
        }

        Object obj = result.getReturnValue();

        if (result.getReturnValue() instanceof Mono) {
            obj = ((Mono<?>) result.getReturnValue()).block();
            if (obj instanceof CommonResult) {
                return writeBody(obj, param, exchange);
            }
        }

        CommonResult body = responseService.getResult(obj);
        body.setSuccess(true);
        body.setCode(responseProperties.getSuccess().getCode());
        body.setMsg(responseProperties.getSuccess().getMsg());

        return writeBody(body, param, exchange);
    }
}