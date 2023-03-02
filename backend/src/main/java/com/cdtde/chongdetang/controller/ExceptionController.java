package com.cdtde.chongdetang.controller;

import com.cdtde.chongdetang.pojo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/9 0:48
 * @Version 1
 */
@ControllerAdvice
@RestController
@Slf4j
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseResult<Object> exceptionHandle(Exception e) {
        ResponseResult<Object> result = new ResponseResult<>();
        result.setStatus("error").setMessage(e.getMessage());
        log.error("Catch Exception: {}", e.getMessage());
        return result;
    }

    @RequestMapping("/error/token")
    public void handleException(HttpServletRequest request) {
        throw (RuntimeException) request.getAttribute("tokenError");
    }
}
