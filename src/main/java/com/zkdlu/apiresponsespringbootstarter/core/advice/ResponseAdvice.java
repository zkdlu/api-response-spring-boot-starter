package com.zkdlu.apiresponsespringbootstarter.core.advice;

import com.zkdlu.apiresponsespringbootstarter.autoconfig.ResponseProperties;
import com.zkdlu.apiresponsespringbootstarter.core.model.CommonResult;
import com.zkdlu.apiresponsespringbootstarter.core.service.ResponseService;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.pattern.PathPatternParser;

@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    private final ResponseService responseService;
    private final ResponseProperties responseProperties;

    public ResponseAdvice(ResponseService responseService, ResponseProperties responseProperties) {
        this.responseService = responseService;
        this.responseProperties = responseProperties;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (containInWhiteList(request)) {
            return body;
        }

        if (body instanceof CommonResult) {
            CommonResult commonResult = (CommonResult) body;
            try {
                HttpStatus status = HttpStatus.valueOf(commonResult.getCode());
                response.setStatusCode(status);
            } catch (IllegalArgumentException e) {
                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return commonResult;
        }

        CommonResult result = responseService.getResult(body);
        result.setSuccess(true);
        result.setCode(responseProperties.getSuccess().getCode());
        result.setMsg(responseProperties.getSuccess().getMsg());

        return result;
    }

    private boolean containInWhiteList(ServerHttpRequest request) {
        return responseProperties.getBlackList()
                .stream()
                .map(url -> new PathPatternParser().parse(url))
                .anyMatch(pathPattern -> pathPattern.matches(PathContainer.parsePath(request.getURI().getPath())));
    }
}
