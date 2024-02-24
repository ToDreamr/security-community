package com.pray.utils;


import cn.hutool.core.bean.BeanUtil;
import com.pray.common.UserHolder;
import com.pray.entity.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

import static com.pray.utils.PrayConstants.LOGIN_USER_KEY;

/**
 * <p>
 * LoginInterceptor
 * <p>
 *
 * @author 春江花朝秋月夜
 * @since 2023/8/24
 */
@Deprecated
public class LoginInterceptor implements HandlerInterceptor {
    private final StringRedisTemplate stringRedisTemplate;

    public LoginInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("authorization");
        if (authorization==null){
            response.setStatus(401);
            return false;
        }
        //写入当前用户信息需要在拦截器里面写，不然为空
        //将Redis里面作为Map存在的UserDto数据 转化为userDto,存入ThreadLocal<UserDto>之中。

        String userKey=LOGIN_USER_KEY+authorization;
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(userKey);
        UserDto userDto=BeanUtil.fillBeanWithMap(map, new UserDto(), true);
        UserHolder.setLocalUser(userDto);
        return true;
    }
}
