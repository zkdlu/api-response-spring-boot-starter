package com.zkdlu.apiresponsespringbootstarter.core.advice;

import com.zkdlu.apiresponsespringbootstarter.autoconfig.ResponseProperties;
import com.zkdlu.apiresponsespringbootstarter.core.model.SingleResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseProperties responseProperties;
    private final ExceptionCallback callback;

    public ExceptionAdvice(final ResponseProperties responseProperties, final ExceptionCallback callback) {
        this.responseProperties = responseProperties;
        this.callback = callback;
    }

    @ExceptionHandler(RuntimeException.class)
    public Object handle(final Exception e) {
        return getResult(e, getExceptionProperties(e, ResponseProperties.ExceptionProperties.UNHANDLED));
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handleNoHandlerFoundException(final Exception e) {
        return getResult(e, getExceptionProperties(e, ResponseProperties.ExceptionProperties.NOT_FOUND));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleNotSupportedMethodException(final Exception e) {
        return getResult(e, getExceptionProperties(e, ResponseProperties.ExceptionProperties.METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object handleMissingRequestParameterException(final Exception e) {
        return getResult(e, getExceptionProperties(e, ResponseProperties.ExceptionProperties.BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleValidationException(final Exception e) {
        return getResult(e, getExceptionProperties(e, ResponseProperties.ExceptionProperties.BAD_REQUEST));
    }

    private ResponseProperties.ExceptionProperties getExceptionProperties(final Exception e, final ResponseProperties.ExceptionProperties unhandled) {
        final ResponseProperties.ExceptionProperties exceptionModel = responseProperties.getExceptions()
                .values().stream()
                .filter(r -> r.getType().equals(e.getClass()))
                .findFirst()
                .orElse(unhandled);

        exceptionModel.setMsg(exceptionModel.getMsg());
        return exceptionModel;
    }

    private SingleResult<Object> getResult(final Exception e, final ResponseProperties.ExceptionProperties exceptionModel) {
        final SingleResult<Object> result = new SingleResult<>();
        result.setSuccess(false);
        result.setCode(exceptionModel.getCode());
        result.setMsg(exceptionModel.getMsg());
        result.setData(e.getMessage());

        notify(result.getCode(), e);

        return result;
    }

    private void notify(int code, Exception e) {
        callback.onException(code, e);
    }
}
