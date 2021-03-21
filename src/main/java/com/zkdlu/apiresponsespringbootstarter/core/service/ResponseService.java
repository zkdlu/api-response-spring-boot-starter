package com.zkdlu.apiresponsespringbootstarter.core.service;

import com.zkdlu.apiresponsespringbootstarter.core.model.SingleResult;
import com.zkdlu.apiresponsespringbootstarter.core.model.CommonResult;
import com.zkdlu.apiresponsespringbootstarter.core.model.ListResult;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ResponseService {
    public <T> CommonResult getResult(T data) {
        return getSingleResult(data);
    }

    private <T> CommonResult getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        return result;
    }

    private <T> CommonResult getListResult(List<T> data) {
        ListResult<T> result = new ListResult<>();
        return result;
    }
}
