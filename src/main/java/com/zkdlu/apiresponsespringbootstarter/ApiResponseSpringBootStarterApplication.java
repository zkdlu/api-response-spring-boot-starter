package com.zkdlu.apiresponsespringbootstarter;

import com.zkdlu.apiresponsespringbootstarter.core.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ApiResponseSpringBootStarterApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApiResponseSpringBootStarterApplication.class, args);
    }

    @Autowired
    ResponseService responseService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
