package com.pray.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BackendBaseLog {
    //日志内容
    String title() default "默认日志";
    //日志分类
    String serviceType() default  "普通服务";
}
