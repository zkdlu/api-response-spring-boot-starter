package com.zkdlu.apiresponsespringbootstarter.core.advice;

import com.zkdlu.apiresponsespringbootstarter.core.model.CommonResult;
import com.zkdlu.apiresponsespringbootstarter.core.model.SingleResult;
import com.zkdlu.apiresponsespringbootstarter.core.service.ResponseService;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    private final ResponseService responseService;

    public ResponseAdvice(ResponseService responseService) {
        this.responseService = responseService;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof CommonResult) {
            return body;
        }

        SingleResult<Object> result = new SingleResult<>();
        result.setSuccess(true);
        result.setCode("SUCCESS");
        result.setMsg("SUCCESS");
        result.setData(body);

        return result;
    }
}
