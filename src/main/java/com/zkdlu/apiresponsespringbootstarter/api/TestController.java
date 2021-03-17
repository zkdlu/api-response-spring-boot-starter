package com.zkdlu.apiresponsespringbootstarter.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/")
    public String test() {
        throw new RuntimeException("무언가");
        //return "hi";
    }
}
