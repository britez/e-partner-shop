package com.epartner.shop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by martin on 17/10/16.
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {


    @ExceptionHandler(EntityPersist.class)
    @ResponseStatus(value = HttpStatus.IM_USED,reason = "Este usuario ya existe")
    public void entityPersist(EntityPersist ex) {
        //handle used category
    }

    @ExceptionHandler(MailCantBeSendException.class)
    @ResponseStatus(value = HttpStatus.IM_USED,reason = "No se puede enviar el e-mail")
    public void mailCantBeSend(MailCantBeSendException ex) {
        //handle used category
    }
}
