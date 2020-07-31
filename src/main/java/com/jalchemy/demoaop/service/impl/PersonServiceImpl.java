package com.jalchemy.demoaop.service.impl;

import com.jalchemy.demoaop.aspect.annotation.PersonRequestVOValidator;
import com.jalchemy.demoaop.dto.PersonRequestVO;
import com.jalchemy.demoaop.exception.PersonServiceException;
import com.jalchemy.demoaop.service.PersonService;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    @PersonRequestVOValidator
    @Override
    public void add(PersonRequestVO vo) throws PersonServiceException {
        // save to db
    }

    @Override
    public boolean isExist(PersonRequestVO vo) throws PersonServiceException {
        // TODO
        return true;
    }

}
