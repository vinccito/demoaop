package com.jalchemy.demoaop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class PerformanceAspect {

    private static final Logger log = LoggerFactory.getLogger(PerformanceAspect.class);

    @Around(value = "@annotation(com.jalchemy.demoaop.aspect.annotation.Performance)")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch sw = new StopWatch();
        sw.start();
        pjp.proceed();
        sw.stop();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        log.info("Cost Millis:{}, Method:{}", sw.getTotalTimeMillis(), signature.getMethod().toGenericString());
    }

}
