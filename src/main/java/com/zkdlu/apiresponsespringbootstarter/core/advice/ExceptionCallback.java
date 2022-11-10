package com.zkdlu.apiresponsespringbootstarter.core.advice;

public abstract class ExceptionCallback {
    public abstract void on500Exception(Exception e);
    public abstract void on400Exception(Exception e);

    public void onException(int code, Exception e) {
        if (code >= 500) on500Exception(e);
        else if (code >= 400) on400Exception(e);
    }
}
