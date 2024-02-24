package com.pray.controller;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.pray.utils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * <p>
 * ExceptionAdvice
 * <p>
 *
 * @author 春江花朝秋月夜
 * @since 2023/9/14 0:23
 */
@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(JWTDecodeException.class)
    public Result<String> run(JWTDecodeException jwtDecodeException){
        return Result.fail(jwtDecodeException.getMessage());
    }
}
