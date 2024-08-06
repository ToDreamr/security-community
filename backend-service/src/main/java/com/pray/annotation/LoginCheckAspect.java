package com.pray.annotation;

import com.pray.common.UserHolder;
import com.pray.entity.dto.UserDto;
import com.pray.entity.po.LoginUser;
import com.pray.utils.JwtUtils;
import com.pray.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
@Order(-1)
public class LoginCheckAspect {
    @Autowired
    private HttpServletRequest request; // 自动注入 request 对象
    @Autowired
    JwtUtils tokenUtils;

    //在方法上来确定切面
    @Around("@annotation(loginCheck)")//保持名字和下面的入参参数一致
    public Object aroundRequestAuthCheck(ProceedingJoinPoint joinPoint, LoginCheck loginCheck) throws Throwable {
        log.info("<---------------进入请求登录验证拦截器-------------->");
        String methodName = joinPoint.getSignature().getName();
        Class<?> clazz = Class.forName(joinPoint.getTarget().getClass().getName());//增强方法类
        //增强类含有的方法
        Method[] methods = clazz.getMethods();
        //找到加了注解的方法
        for (Method method : methods) {
            if (method.getAnnotation(LoginCheck.class) != null) {
                log.info("<---------------找到增强方法-------------->");
                String authorization = request.getHeader("authorization");
                if (authorization == null) {
                    return Result.fail("未登录，请前往登录！！");
                }
                LoginUser user = tokenUtils.toUser(tokenUtils.resolve(authorization));

                UserDto userDto = new UserDto();
                userDto.setUsername(user.getUsername());

                UserHolder.setLocalUser(userDto);
                log.info("<---------------增强方法结束,本地线程设置用户信息：{}-------------->", UserHolder.getLocalUser().toString());
            }
        }
        return joinPoint.proceed();
    }
    //设置环绕的注解是需要设置方法的，或者其他方法，这里使用方法来实现
}
