package com.zkdlu.apiresponsespringbootstarter.core.advice;

import com.zkdlu.apiresponsespringbootstarter.autoconfig.ExceptionModel;
import com.zkdlu.apiresponsespringbootstarter.autoconfig.ResponseProperties;
import com.zkdlu.apiresponsespringbootstarter.core.advice.exception.NotDefineException;
import com.zkdlu.apiresponsespringbootstarter.core.model.SingleResult;
import com.zkdlu.apiresponsespringbootstarter.core.service.ResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseProperties responseProperties;

    public ExceptionAdvice(ResponseProperties responseProperties) {
        this.responseProperties = responseProperties;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handle(HttpServletRequest request, Exception e) {
        SingleResult<Object> result = new SingleResult<>();

        ExceptionModel exceptionModel = responseProperties.getExceptions()
                .stream()
                .filter(r -> r.getType().equals(e.getClass()))
                .findFirst().orElseThrow(NotDefineException::new);

        result.setSuccess(false);
        result.setCode(exceptionModel.getCode());
        result.setMsg(exceptionModel.getMsg());

        return result;
    }
}
