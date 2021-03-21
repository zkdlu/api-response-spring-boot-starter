package com.zkdlu.apiresponsespringbootstarter.core.model;

import java.util.List;

public class ListResult<T> extends CommonResult {
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
