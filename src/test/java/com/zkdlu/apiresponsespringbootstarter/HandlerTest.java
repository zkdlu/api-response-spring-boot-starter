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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                .addDispatcherServletCustomizer(dispatcherServlet -> dispatcherServlet.setThrowExceptionIfNoHandlerFound(true))
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
                .andExpect(jsonPath("$.msg", equalTo("Custom Message")))
                .andExpect(jsonPath("$.data", equalTo("메시지")))
        ;
    }

    @Test
    void 없는_api_test() throws Exception {
        mockMvc.perform(get("/asdf"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success", equalTo(false)))
                .andExpect(jsonPath("$.code", equalTo(404)))
                .andExpect(jsonPath("$.msg", equalTo("Not Found")))
                .andExpect(jsonPath("$.data", equalTo("No handler found for GET /asdf")))
        ;
    }

    @Test
    void 요청파라미터누락_test() throws Exception {
        mockMvc.perform(get("/3"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", equalTo(false)))
                .andExpect(jsonPath("$.code", equalTo(400)))
                .andExpect(jsonPath("$.msg", equalTo("Bad Request")))
                .andExpect(jsonPath("$.data", equalTo("Required String parameter 'param' is not present")))
        ;
    }

    @Test
    void 지원하지않는_http_method_test() throws Exception {
        mockMvc.perform(post("/exception"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.success", equalTo(false)))
                .andExpect(jsonPath("$.code", equalTo(405)))
                .andExpect(jsonPath("$.msg", equalTo("Method Not Allowed")))
                .andExpect(jsonPath("$.data", equalTo("Request method 'POST' not supported")))
        ;
    }

    @Test
    void 지원하지않는_http_status_test() throws Exception {
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
                .andExpect(jsonPath("$.data", equalTo("메시지")))
        ;
    }

    @Test
    void 처리하지않는_예외_test() throws Exception {
        ResponseService responseService = new ResponseService();
        ResponseProperties responseProperties = getResponseProperties3();

        mockMvc = MockMvcBuilders.standaloneSetup(new DemoApi())
                .setControllerAdvice(
                        new ExceptionAdvice(responseProperties),
                        new ResponseAdvice(responseService, responseProperties))
                .build();

        mockMvc.perform(get("/exception"))
                .andDo(print())
                .andExpect(status().isNotImplemented())
                .andExpect(jsonPath("$.success", equalTo(false)))
                .andExpect(jsonPath("$.code", equalTo(501)))
                .andExpect(jsonPath("$.msg", equalTo("Unhandled Exception")))
                .andExpect(jsonPath("$.data", equalTo("메시지")))
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

    private ResponseProperties getResponseProperties3() {
        ResponseProperties responseProperties = new ResponseProperties();
        responseProperties.setSuccess(getSuccessProperties());

        responseProperties.setExceptions(getExceptions3());

        return responseProperties;
    }

    private Map<String, ExceptionProperties> getExceptions() {
        ExceptionProperties exceptionProperties = new ExceptionProperties();
        exceptionProperties.setCode(400);
        exceptionProperties.setMsg("Custom Message");
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

    private Map<String, ExceptionProperties> getExceptions3() {
        return new HashMap<>();
    }


    private SuccessProperties getSuccessProperties() {
        SuccessProperties successProperties = new SuccessProperties();
        successProperties.setCode(200);
        successProperties.setMsg("OK");
        return successProperties;
    }
}
