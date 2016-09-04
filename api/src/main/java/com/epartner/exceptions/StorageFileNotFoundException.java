package com.epartner.exceptions;

/**
 * Created by maty on 4/9/16.
 */
public class StorageFileNotFoundException extends RuntimeException {

    public StorageFileNotFoundException() {
    }

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageFileNotFoundException(Throwable cause) {
        super(cause);
    }

    public StorageFileNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
