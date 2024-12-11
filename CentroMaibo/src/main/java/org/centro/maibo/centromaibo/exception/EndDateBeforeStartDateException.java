package org.centro.maibo.centromaibo.exception;

public class EndDateBeforeStartDateException extends RuntimeException {

    public EndDateBeforeStartDateException(String message) {
        super(message);
    }

    public EndDateBeforeStartDateException(String message, Throwable cause) {
        super(message, cause);
    }
}