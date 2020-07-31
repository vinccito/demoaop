package com.jalchemy.demoaop.aspect;

import com.jalchemy.demoaop.dto.PersonRequestVO;
import com.jalchemy.demoaop.exception.PersonServiceException;
import com.jalchemy.demoaop.service.PersonService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Aspect
@Component
public class PersonRequestVOValidatorAspect {

    @Resource
    private PersonService personService;

    @Around(value = "@annotation(com.jalchemy.demoaop.aspect.annotation.PersonRequestVOValidator)")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        PersonRequestVO vo = (PersonRequestVO) pjp.getArgs()[0];
        boolean isExist = personService.isExist(vo);
        if (!isExist) {
            pjp.proceed();
        } else {
            throw new PersonServiceException("params.invalid");
        }
    }

}
