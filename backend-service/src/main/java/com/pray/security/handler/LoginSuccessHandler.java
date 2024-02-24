package com.pray.security.handler;

import com.pray.entity.po.LoginUser;
import com.pray.entity.vo.response.AuthorizeVO;
import com.pray.service.AccountService;
import com.pray.utils.JwtUtils;
import com.pray.utils.PrayConstants;
import com.pray.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * LoginSuccessHandler
 * <p>
 *
 * @author 春江花朝秋月夜
 * @since 2023/9/6 13:30
 */
@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    AccountService accountService;
    JwtUtils jwtUtils;
    StringRedisTemplate stringRedisTemplate;

    /**
     * 注入所需的Bean
     */
    public LoginSuccessHandler(AccountService accountService, JwtUtils jwtUtils,StringRedisTemplate stringRedisTemplate) {
        this.accountService = accountService;
        this.jwtUtils = jwtUtils;
        this.stringRedisTemplate=stringRedisTemplate;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        //获取Spring Security封装的User对象，获取的Object对象直接就是继承了UserDetails接口的用户数据对象，无须进行强制转换
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        log.info("通过SpringSecurity实现成功登录,principal:{}",principal);

        String jwtToken = jwtUtils.createJwt(principal, principal.getUsername());

        String userKey = PrayConstants.LOGIN_USER_KEY + jwtToken;
        //构建视图层返回对象
        AuthorizeVO vo = new AuthorizeVO();
        vo.setToken(jwtToken);
        vo.setUsername(principal.getUsername());

        stringRedisTemplate.opsForValue().set(userKey, String.valueOf(vo));
        stringRedisTemplate.expire(userKey,PrayConstants.LOGIN_USER_TTL, TimeUnit.SECONDS);
        response.setContentType("text/json;charset=utf-8");
        //前端返回登录成功信息
        response.getWriter().write(Result.ok(vo).JsonResult());
    }

}
