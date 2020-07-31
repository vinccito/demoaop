package com.jalchemy.demoaop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
public class ShowerAspect {

    private static final Logger log = LoggerFactory.getLogger(ShowerAspect.class);

    @Around(value = "@annotation(com.jalchemy.demoaop.aspect.annotation.Shower)")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        pjp.proceed();
        log.info("去洗澡");
    }

}
