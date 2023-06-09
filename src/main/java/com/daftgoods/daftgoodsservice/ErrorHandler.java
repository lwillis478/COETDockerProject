package com.daftgoods.daftgoodsservice;

import com.daftgoods.daftgoodsservice.core.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorHandler {
    @ResponseBody
    @ExceptionHandler(UserLoginException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> userLoginHandler(UserLoginException uLogE)
    {
        return new HashMap<>() {
            { put("statuscode", "404"); }
            { put("message", uLogE.getMessage()); }
        };
    }
}
