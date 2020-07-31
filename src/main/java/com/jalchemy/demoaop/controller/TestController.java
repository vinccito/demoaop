package com.jalchemy.demoaop.controller;

import com.jalchemy.demoaop.aspect.annotation.Breakfast;
import com.jalchemy.demoaop.aspect.annotation.IdempotentValidator;
import com.jalchemy.demoaop.aspect.annotation.Performance;
import com.jalchemy.demoaop.aspect.annotation.Shower;
import com.jalchemy.demoaop.dto.PersonRequestVO;
import com.jalchemy.demoaop.exception.PersonServiceException;
import com.jalchemy.demoaop.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/demoaop/api/v1/test")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Resource
    private PersonService personService;

    @Breakfast //增加Breakfast注解
    @Shower    //增加Shower注解
    @RequestMapping(value = "/aop", method = RequestMethod.GET)
    public ResponseEntity testAop() {
        log.info("上班");
        return ResponseEntity.ok().build();
    }

    @Performance
    @RequestMapping(value = "/performance", method = RequestMethod.GET)
    public ResponseEntity testPerformance() throws InterruptedException {
        //log.info("上班打卡");
        Thread.sleep(2 * 1000);//模拟方法耗时
        //log.info("下班打卡");
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/params", method = RequestMethod.POST)
    public ResponseEntity testParamsValid(@RequestBody PersonRequestVO vo) throws PersonServiceException {
        personService.add(vo);
        return ResponseEntity.ok().build();
    }

    @IdempotentValidator(expireInMilliSeconds = 5000, paramsIndex = {0})
    @RequestMapping(value = "/idempotent", method = RequestMethod.POST)
    public ResponseEntity testIdempotent(@RequestBody PersonRequestVO vo) throws PersonServiceException {
        return ResponseEntity.ok().build();
    }

}
