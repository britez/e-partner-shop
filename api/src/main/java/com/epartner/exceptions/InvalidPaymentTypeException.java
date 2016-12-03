package com.epartner.exceptions;

/**
 * Created by mapsi on 12/1/16.
 */
public class InvalidPaymentTypeException extends RuntimeException{

    public InvalidPaymentTypeException() {
    }

    public InvalidPaymentTypeException(String message) {
        super(message);
    }

    public InvalidPaymentTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
