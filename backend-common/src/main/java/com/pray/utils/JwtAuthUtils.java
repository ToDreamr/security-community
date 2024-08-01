package com.pray.utils;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * JwtAuthUtils
 *
 * @author 春江花朝秋月夜
 * @since 2024/2/22 23:17
 */
@Component
public class JwtAuthUtils {
    @Value("${spring.security.jwt.key}")
    public  String SECRET_KEY;
    @Value("${spring.security.jwt.expire}")
    private long expireTime;
    private final String header="authorization";
    private String getToken(HttpServletRequest request)
    {
        String token = request.getHeader(header);
        if (StrUtil.isNotEmpty(token))
        {
            return token;
        }
        return null;
    }
}
