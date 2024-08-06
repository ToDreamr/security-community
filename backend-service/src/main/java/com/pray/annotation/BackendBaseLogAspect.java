package com.pray.annotation;

import com.pray.common.BackendLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * LogAspect
 *
 * @author 春江花朝秋月夜
 * @since 2023/11/2 18:31
 */
@Aspect
@Component
@Slf4j
public class BackendBaseLogAspect {
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @AfterReturning("@annotation(logAspect)")
    public  void afterReturning(JoinPoint joinPoint, BackendBaseLog logAspect){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        BackendLog backendLog = createPrayLog(joinPoint, request, logAspect, null);
        log.info("{},执行常规操作【{}】", backendLog.getPrayName(), backendLog.getTitle());
    }
    //执行错误日志
    @AfterThrowing(value = "@annotation(logAspect)", throwing = "exception", argNames = "joinPoint,logAspect,exception")
    public  void  afterThrowing(JoinPoint joinPoint, BackendBaseLog logAspect, Exception exception){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        BackendLog backendLog = createPrayLog(joinPoint, request, logAspect, null);
        log.error("{},执行异常操作【{}】", backendLog.getPrayName(), backendLog.getTitle(),exception);
    }
    //日志工厂
    //真正的实现代码:
    private BackendLog createPrayLog(JoinPoint joinPoint, HttpServletRequest request, BackendBaseLog log, Exception exception){
        BackendLog backendLog = new BackendLog();
        backendLog.setPrayName(log.title());
        backendLog.setServiceType(log.serviceType());

        //是否抛出异常
        if (exception!=null){
            backendLog.setStatus(500);
            backendLog.setErrorMsg(exception.getMessage());
        }else {
            backendLog.setStatus(200);
        }
        backendLog.setPrayIp(request.getRemoteAddr());
        backendLog.setRequestMethod(request.getMethod());
        backendLog.setPrayUrl(request.getRequestURI());
        String method = joinPoint.getSignature().getName();
        backendLog.setMethod(method);
        return backendLog;
    }
}
