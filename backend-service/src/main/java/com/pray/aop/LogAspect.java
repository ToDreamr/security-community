//package com.pray.aop;
//
//import com.pray.annotation.Log;
//import com.pray.common.PrayLog;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import java.util.Objects;
//
///**
// * LogAspect
// *
// * @author 春江花朝秋月夜
// * @since 2023/11/2 18:31
// */
//@Aspect
//@Component
//@Slf4j
//public class LogAspect {
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @AfterReturning("@annotation(logAspect)")
//    public  void afterReturning(JoinPoint joinPoint, Log logAspect){
//        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
//        PrayLog prayLog = createPrayLog(joinPoint, request, logAspect, null);
//        log.info("{},执行常规操作【{}】",prayLog.getPrayName(),prayLog.getTitle());
//    }
//    //执行错误日志
//    @AfterThrowing(value = "@annotation(logAspect)", throwing = "exception", argNames = "joinPoint,logAspect,exception")
//    public  void  afterThrowing(JoinPoint joinPoint,Log logAspect,Exception exception){
//        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
//        PrayLog prayLog = createPrayLog(joinPoint, request, logAspect, null);
//        log.error("{},执行异常操作【{}】",prayLog.getPrayName(),prayLog.getTitle(),exception);
//    }
//    //日志工厂
//    private PrayLog createPrayLog(JoinPoint joinPoint, HttpServletRequest request, Log log, Exception exception){
//        PrayLog prayLog = new PrayLog();
//        prayLog.setPrayName(log.title());
//        prayLog.setServiceType(log.serviceType());
//
//        //是否抛出异常
//        if (exception!=null){
//            prayLog.setStatus(500);
//            prayLog.setErrorMsg(exception.getMessage());
//        }else {
//            prayLog.setStatus(200);
//        }
//        prayLog.setPrayIp(request.getRemoteAddr());
//        prayLog.setRequestMethod(request.getMethod());
//        prayLog.setPrayUrl(request.getRequestURI());
//        String method = joinPoint.getSignature().getName();
//        prayLog.setMethod(method);
//        return prayLog;
//    }
//}
