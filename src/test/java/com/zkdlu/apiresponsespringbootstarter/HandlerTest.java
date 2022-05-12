package com.zkdlu.apiresponsespringbootstarter;

import com.zkdlu.apiresponsespringbootstarter.autoconfig.ResponseProperties;
import com.zkdlu.apiresponsespringbootstarter.autoconfig.ResponseProperties.ExceptionProperties;
import com.zkdlu.apiresponsespringbootstarter.autoconfig.ResponseProperties.SuccessProperties;
import com.zkdlu.apiresponsespringbootstarter.core.advice.ExceptionAdvice;
import com.zkdlu.apiresponsespringbootstarter.core.advice.ResponseAdvice;
import com.zkdlu.apiresponsespringbootstarter.core.service.ResponseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        ResponseService responseService = new ResponseService();
        ResponseProperties responseProperties = getResponseProperties();

        mockMvc = MockMvcBuilders.standaloneSetup(new DemoApi())
                .setControllerAdvice(
                        new ExceptionAdvice(responseProperties),
                        new ResponseAdvice(responseService, responseProperties))
                .build();
    }

    @Test
    void 성공_api_test() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", equalTo(true)))
                .andExpect(jsonPath("$.code", equalTo(200)))
                .andExpect(jsonPath("$.msg", equalTo("OK")))
                .andExpect(jsonPath("$.data.value", equalTo("hi")))
        ;
    }

    @Test
    void 성공_api2_test() throws Exception {
        mockMvc.perform(get("/2"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success", equalTo(true)))
                .andExpect(jsonPath("$.code", equalTo(200)))
                .andExpect(jsonPath("$.msg", equalTo("OK")))
                .andExpect(jsonPath("$.data.value", equalTo("hi")))
        ;
    }

    @Test
    void 실패_api_test() throws Exception {
        mockMvc.perform(get("/exception"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", equalTo(false)))
                .andExpect(jsonPath("$.code", equalTo(400)))
                .andExpect(jsonPath("$.msg", equalTo("BAD REQUEST")))
                .andExpect(jsonPath("$.data", equalTo(null)))
        ;
    }

    @Test
    void 지원하지않는_상태코드() throws Exception {
        ResponseService responseService = new ResponseService();
        ResponseProperties responseProperties = getResponseProperties2();

        mockMvc = MockMvcBuilders.standaloneSetup(new DemoApi())
                .setControllerAdvice(
                        new ExceptionAdvice(responseProperties),
                        new ResponseAdvice(responseService, responseProperties))
                .build();

        mockMvc.perform(get("/exception"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success", equalTo(false)))
                .andExpect(jsonPath("$.code", equalTo(4000)))
                .andExpect(jsonPath("$.msg", equalTo("지원하지 않는 상태코드")))
                .andExpect(jsonPath("$.data", equalTo(null)))
        ;
    }

    private ResponseProperties getResponseProperties() {
        ResponseProperties responseProperties = new ResponseProperties();
        responseProperties.setSuccess(getSuccessProperties());

        responseProperties.setExceptions(getExceptions());

        return responseProperties;
    }

    private ResponseProperties getResponseProperties2() {
        ResponseProperties responseProperties = new ResponseProperties();
        responseProperties.setSuccess(getSuccessProperties());

        responseProperties.setExceptions(getExceptions2());

        return responseProperties;
    }

    private Map<String, ExceptionProperties> getExceptions() {
        ExceptionProperties exceptionProperties = new ExceptionProperties();
        exceptionProperties.setCode(400);
        exceptionProperties.setMsg("BAD REQUEST");
        exceptionProperties.setType(RuntimeException.class);

        Map<String, ExceptionProperties> map = new HashMap<>();
        map.put("err", exceptionProperties);
        return map;
    }

    private Map<String, ExceptionProperties> getExceptions2() {
        ExceptionProperties exceptionProperties = new ExceptionProperties();
        exceptionProperties.setCode(4000);
        exceptionProperties.setMsg("지원하지 않는 상태코드");
        exceptionProperties.setType(RuntimeException.class);

        Map<String, ExceptionProperties> map = new HashMap<>();
        map.put("err", exceptionProperties);
        return map;
    }

    private SuccessProperties getSuccessProperties() {
        SuccessProperties successProperties = new SuccessProperties();
        successProperties.setCode(200);
        successProperties.setMsg("OK");
        return successProperties;
    }
}