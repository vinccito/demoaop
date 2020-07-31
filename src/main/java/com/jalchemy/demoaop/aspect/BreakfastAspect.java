package com.jalchemy.demoaop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class BreakfastAspect {

    private static final Logger log = LoggerFactory.getLogger(BreakfastAspect.class);

    @Around(value = "@annotation(com.jalchemy.demoaop.aspect.annotation.Breakfast)")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("吃早餐");
        pjp.proceed();
    }

}
