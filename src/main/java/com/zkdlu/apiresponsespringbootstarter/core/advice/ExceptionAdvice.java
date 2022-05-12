package com.zkdlu.apiresponsespringbootstarter.core.advice;

import com.zkdlu.apiresponsespringbootstarter.autoconfig.ResponseProperties;
import com.zkdlu.apiresponsespringbootstarter.autoconfig.ResponseProperties.ExceptionProperties;
import com.zkdlu.apiresponsespringbootstarter.core.model.SingleResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseProperties responseProperties;

    public ExceptionAdvice(ResponseProperties responseProperties) {
        this.responseProperties = responseProperties;
    }

    @ExceptionHandler(RuntimeException.class)
    public Object handle(Exception e) {
        return getResult(getExceptionProperties(e, ExceptionProperties.UNHANDLED));
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handleNoHandlerFoundException(Exception e) {
        return getResult(getExceptionProperties(e, ExceptionProperties.NOT_FOUND));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleNotSupportedMethodException(Exception e) {
        return getResult(getExceptionProperties(e, ExceptionProperties.METHOD_NOT_ALLOWED));
    }

    private ExceptionProperties getExceptionProperties(Exception e, ExceptionProperties unhandled) {
        ExceptionProperties exceptionModel = responseProperties.getExceptions()
                .values().stream()
                .filter(r -> r.getType().equals(e.getClass()))
                .findFirst()
                .orElse(unhandled);
        return exceptionModel;
    }

    private SingleResult<Object> getResult(ExceptionProperties exceptionModel) {
        SingleResult<Object> result = new SingleResult<>();
        result.setSuccess(false);
        result.setCode(exceptionModel.getCode());
        result.setMsg(exceptionModel.getMsg());
        return result;
    }
}
