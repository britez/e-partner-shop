package com.epartner.exceptions;

/**
 * Created by mapsi on 12/14/16.
 */
public class MeliNotConfiguredException extends RuntimeException {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
