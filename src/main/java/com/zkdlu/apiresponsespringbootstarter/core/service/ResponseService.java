package com.zkdlu.apiresponsespringbootstarter.core.service;

import com.zkdlu.apiresponsespringbootstarter.autoconfiguration.config.ResponseConfig;
import com.zkdlu.apiresponsespringbootstarter.core.model.CommonResult;
import com.zkdlu.apiresponsespringbootstarter.core.model.ListResult;
import com.zkdlu.apiresponsespringbootstarter.core.model.SingleResult;
import org.apache.coyote.Response;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ResponseService {
    private final ResponseConfig responseConfig;

    public ResponseService() {
        responseConfig = new ResponseConfig();
    }

    public ResponseService(ResponseConfig responseConfig) {
        this.responseConfig = responseConfig;
    }

    public <T> CommonResult getResult(T data) {
        if (data instanceof Collection) {
            return getListResult((List)data);
        } else {
            return getSingleResult(data);
        }
    }

    private <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        return result;
    }

    private <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        return result;
    }

    public void setSuccesss(CommonResult result) {
        result.setSuccess(true);
        setCodeAndMessage(result,
                (String) responseConfig.get("success-code"),
                (String) responseConfig.get("success-msg"));
    }

    public void setFail(CommonResult result, String code, String message) {
        result.setSuccess(false);
        setCodeAndMessage(result, code, message);
    }

    private void setCodeAndMessage(CommonResult result, String code, String message) {
        result.setCode(code);
        result.setMsg(message);
    }
}
