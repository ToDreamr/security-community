package com.pray.security.filter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pray.entity.po.LoginUser;
import com.pray.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * <p>
 * JwtAuthenticationFilter
 * <p>
 *
 * @author 春江花朝秋月夜
 * @since 2023/9/4 14:10
 */
@Slf4j
@Component
//只需要继承这个每次请求过滤器即可完成接口请求的认证
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Resource
    JwtUtils utils;

    /**
     * 登录之前先进入这个过滤器
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //从请求头中获取响应认证
        String requestURI = request.getRequestURI();

        String authorization = request.getHeader("Authorization");
        log.info("请求路径：{}，尝试获取头部authorization：{}",requestURI,authorization);
        switch (requestURI){
            case "/auth/code" -> {
                filterChain.doFilter(request, response);
            }
            case "/auth/login" -> {
                if (authorization!=null){
                    if (utils.toUser(utils.resolve(authorization))!=null) {
                        filterChain.doFilter(request, response);
                    }
                    else {
                        response.setStatus(401);
                        return;
                    }
                }
            }
        }
        try {
           loginCheck(request,response,filterChain,authorization,utils);
        }catch (JWTDecodeException e){
            e.printStackTrace();
           log.error("访问权限失败，用户token错误 "+e.getMessage());
        }
    }
    private void loginCheck(HttpServletRequest request, @NotNull HttpServletResponse response, FilterChain filterChain,String authorization,JwtUtils utils) throws ServletException, IOException {
        DecodedJWT decodedJWT = utils.resolve(authorization);
        if (decodedJWT!=null){
            LoginUser user = utils.toUser(decodedJWT);
            //在下面实现对用户名密码权限框架的判断
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);//安全上下文
            request.setAttribute("user",utils.toUser(decodedJWT));
            request.authenticate(response);//最后认证完成
        }
        filterChain.doFilter(request,response);
    }
}
