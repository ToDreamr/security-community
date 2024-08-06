package com.pray.security.filter;

import cn.hutool.core.exceptions.ValidateException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * <p>
 * ValidateFilter
 * <p>
 *
 * @author 春江花朝秋月夜
 * @since 2023/9/11 11:57
 */
@Deprecated
@Slf4j
public class ValidateFilter extends OncePerRequestFilter {
    AuthenticationFailureHandler authenticationFailureHandler= (request, response, exception) -> {
        log.info("异常信息：{}",exception.getMessage());
        response.sendRedirect("/auth/login");
    };
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //分别请求是否是登录请求
        if (!("login".equals(request.getRequestURI())&&request.getMethod().equals("POST"))){
            filterChain.doFilter(request,response);
        }else {
            try {
                imgValidateCode(request,response);
                filterChain.doFilter(request,response);
            }catch (ValidateException e){
                log.error("验证失败："+e.getMessage());
            }
        }
    }

    private void imgValidateCode(HttpServletRequest request, HttpServletResponse response) {
        //验证信息：
        String code = request.getParameter("code");
    }
}
