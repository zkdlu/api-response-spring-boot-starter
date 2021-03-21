package com.zkdlu.apiresponsespringbootstarter.core.advice.exception;

public class NotDefineException extends RuntimeException{
    public NotDefineException() {
        super("처리되지 않은 예외");
    }
}
