package com.zkdlu.apiresponsespringbootstarter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class DemoApi {
    @GetMapping
    public Demo demo() {
        return new Demo("hi");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/2")
    public Demo demo1() {
        return new Demo("hi");
    }
    @GetMapping("/3")
    public Demo demo2(@RequestParam String param) {
        return new Demo("hi3");
    }

    @GetMapping("exception")
    public Demo demo2() {
        throw new RuntimeException("메시지");
    }

    @GetMapping("valid")
    public Demo demo3(@RequestBody @Valid Demo demo) {
        return demo;
    }

    static class Demo {

        public Demo() {
        }

        @NotNull
        private String value;

        public Demo(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
