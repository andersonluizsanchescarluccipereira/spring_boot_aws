package com.ads.demo.application.exceptions;

public class CustomerPersistenceException extends RuntimeException {
    public CustomerPersistenceException() {
    }

    public CustomerPersistenceException(String message) {
        super(message);
    }

    public CustomerPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerPersistenceException(Throwable cause) {
        super(cause);
    }

    public CustomerPersistenceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
