package com.example.shirodemo.controller;

import com.example.shirodemo.utils.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(value = AuthorizationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result errorHandler(Exception ex) {
        return Result.failed(-3, "没权限");
    }

}
