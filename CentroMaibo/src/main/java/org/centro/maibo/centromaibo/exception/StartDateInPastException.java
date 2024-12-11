package org.centro.maibo.centromaibo.exception;

public class StartDateInPastException extends RuntimeException {

    public StartDateInPastException(String message) {
        super(message);
    }

    public StartDateInPastException(String message, Throwable cause) {
        super(message, cause);
    }
}