package com.jalchemy.demoaop.aspect;

import com.jalchemy.demoaop.aspect.annotation.IdempotentValidator;
import com.jalchemy.demoaop.config.RedisClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class IdempotentValidatorAspect {

    private static final Logger log = LoggerFactory.getLogger(IdempotentValidatorAspect.class);

    private static final String PREFIX_IDEMPOTENT = "Idempotent::";

    @Resource
    private RedisClient redisClient;

    @Around(value = "@annotation(com.jalchemy.demoaop.aspect.annotation.IdempotentValidator)")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        boolean isDuplicate = false;

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        IdempotentValidator annotation = signature.getMethod().getAnnotation(IdempotentValidator.class);
        int parameterCount = signature.getMethod().getParameterCount();

        String signatureKey = getSignature(annotation, parameterCount, pjp.getArgs());
        if (signatureKey != null) {
            String key = PREFIX_IDEMPOTENT + signatureKey;
            Long r = redisClient.increment(key);
            if (r != null && r.longValue() == 1L) {
                log.info("first time");
                redisClient.expire(key, annotation.expireInMilliSeconds(), TimeUnit.MILLISECONDS);
            } else {
                isDuplicate = true;
            }
        }

        if (!isDuplicate) {
            pjp.proceed();
        } else {
            log.warn("Duplicate request");
        }
    }

    private String getSignature(IdempotentValidator annotation, int parameterCount, Object[] args) {
        String signature = null;
        int[] paramsIndexes = annotation.paramsIndex();
        if (paramsIndexes != null && paramsIndexes.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i : paramsIndexes) {
                if (i + 1 > parameterCount) {
                    continue;
                }
                Object o = args[i];
                sb.append(o.toString());
            }
            if (!StringUtils.isEmpty(sb.toString())) {
                signature = getMD5(sb.toString());
            }
        }
        return signature;
    }

    private String getMD5(String plainText) {
        return DigestUtils.md5DigestAsHex(plainText.getBytes());
    }

}
