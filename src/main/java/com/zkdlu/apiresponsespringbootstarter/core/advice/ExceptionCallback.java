package com.zkdlu.apiresponsespringbootstarter.core.advice;

public interface ExceptionCallback {
    void on500Exception(Exception e);
    void on400Exception(Exception e);
}
