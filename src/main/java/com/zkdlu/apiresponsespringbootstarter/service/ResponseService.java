package com.zkdlu.apiresponsespringbootstarter.service;

import com.zkdlu.apiresponsespringbootstarter.model.CommonResult;
import com.zkdlu.apiresponsespringbootstarter.model.ListResult;
import com.zkdlu.apiresponsespringbootstarter.model.SingleResult;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ResponseService {
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
}
