package com.campanha_core.domain.exceptions;

public class ServiceException extends Exception {

    public ServiceException() {
        super();
    }

    public ServiceException(final String message) {
        super(message);
    }

}
