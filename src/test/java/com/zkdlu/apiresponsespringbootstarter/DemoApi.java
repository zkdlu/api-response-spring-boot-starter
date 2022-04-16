package com.zkdlu.apiresponsespringbootstarter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("exception")
    public Demo demo2() {
        throw new RuntimeException();
    }

    static class Demo {
        private String value;

        public Demo(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
