package com.jalchemy.demoaop.exception;

public class PersonServiceException extends Exception {

    public PersonServiceException() {
    }

    public PersonServiceException(String message) {
        super(message);
    }

    public PersonServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersonServiceException(Throwable cause) {
        super(cause);
    }

    public PersonServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
