package com.zkdlu.apiresponsespringbootstarter.core.advice;

import com.zkdlu.apiresponsespringbootstarter.autoconfig.ResponseProperties;
import com.zkdlu.apiresponsespringbootstarter.core.advice.exception.NotDefineException;
import com.zkdlu.apiresponsespringbootstarter.core.model.SingleResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseProperties responseProperties;

    public ExceptionAdvice(ResponseProperties responseProperties) {
        this.responseProperties = responseProperties;
    }

    @ExceptionHandler(RuntimeException.class)
    public Object handle(HttpServletRequest request, Exception e) {
        SingleResult<Object> result = new SingleResult<>();

        ResponseProperties.ExceptionProperties exceptionModel = responseProperties.getExceptions()
                .values().stream().filter(r -> r.getType().equals(e.getClass()))
                .findFirst().orElseThrow(NotDefineException::new);

        result.setSuccess(false);
        result.setCode(exceptionModel.getCode());
        result.setMsg(exceptionModel.getMsg());

        return result;
    }
}
