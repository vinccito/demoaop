package com.jalchemy.demoaop.aspect.annotation;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IdempotentValidator {

    int[] paramsIndex() default {};

    long expireInMilliSeconds() default 86400000;

}
