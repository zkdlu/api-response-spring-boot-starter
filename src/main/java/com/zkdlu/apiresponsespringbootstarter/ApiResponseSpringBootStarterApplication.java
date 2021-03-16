package com.zkdlu.apiresponsespringbootstarter;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.zkdlu.apiresponsespringbootstarter.model.ListResult;
import com.zkdlu.apiresponsespringbootstarter.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
        List<String> list = new ArrayList<>();
        String hello = "hello";

        list.add(hello);

        ListResult<String> result = (ListResult<String>)responseService.getResult(list);
        for (var i : result.getList()) {
            System.out.println(i);
        }
    }
}
