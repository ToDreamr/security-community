package com.pray.security.handler;

import com.pray.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p>
 * LoginFailureHandler
 * <p>
 *
 * @author 春江花朝秋月夜
 * @since 2023/9/6 13:38
 */
@Slf4j
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        e.printStackTrace();
        //判断出现的异常类型分别控制跳转页面
        String errMsg = "";
        //捕获到当前用户不存在这个异常
        if (e instanceof UsernameNotFoundException |e instanceof BadCredentialsException){
            errMsg="不存在该用户或者当前用户证书已经过期";
        } else if (e instanceof AccountExpiredException) {
            errMsg="账户过期，请联系管理员";
        } else if (e instanceof DisabledException) {
            errMsg="当前账户已经被禁用，请联系管理员";
        }else {
          log.error("登录失败",e);
        }
        log.error("登录失败：{}",errMsg);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(Result.fail(404,errMsg).JsonResult());
    }
}
