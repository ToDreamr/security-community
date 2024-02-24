package com.pray.security.handler;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.pray.utils.JwtUtils;
import com.pray.utils.PrayConstants;
import com.pray.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p>
 * LogOutHandler
 * <p>
 *
 * @author 春江花朝秋月夜
 * @since 2023/9/6 14:04
 */
@Component
@Slf4j
public class LogOutHandler implements LogoutSuccessHandler {
    //使用构造方法获取的解析器
    RedisTemplate<Object,Object> redisTemplate;

    public LogOutHandler(RedisTemplate stringRedisTemplate, JwtUtils jwtUtils) {
        this.redisTemplate = stringRedisTemplate;
        this.jwtUtils = jwtUtils;
    }
    //快捷键：ALT+Inset

    //需要引入token的有效依赖：
    JwtUtils jwtUtils;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        //获取当前用户账号：
        String authorization = request.getHeader("Authorization");
        log.info("接收到退出登录请求，authorization:{}",authorization);
        DecodedJWT jwt = jwtUtils.resolve(authorization);

        UserDetails user = jwtUtils.toUser(jwt);
        String userKey = PrayConstants.LOGIN_USER_KEY + authorization;

        //清除浏览器服务器颁发的信任token
        Boolean delete = redisTemplate.delete(userKey);
        response.setContentType("application/json;charset=utf-8");
        if (Boolean.FALSE.equals(delete)){
            response.setStatus(200);
            String msg = "用户 " + user.getUsername() + "退出失败";
            response.getWriter().write(Result.message(200,msg).JsonResult());
            System.out.println(response);
        }
        else {
            response.setStatus(200);
            response.getWriter().write(Result.message(200,"用户 "+user.getUsername()+"退出成功").JsonResult());
        }
    }
}
