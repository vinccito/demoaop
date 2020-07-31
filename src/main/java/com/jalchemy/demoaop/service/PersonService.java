package com.jalchemy.demoaop.service;

import com.jalchemy.demoaop.dto.PersonRequestVO;
import com.jalchemy.demoaop.exception.PersonServiceException;

public interface PersonService {

    void add(PersonRequestVO vo) throws PersonServiceException;

    boolean isExist(PersonRequestVO vo) throws PersonServiceException;

}
