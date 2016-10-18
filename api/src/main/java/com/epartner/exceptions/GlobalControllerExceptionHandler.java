package com.epartner.exceptions;

import com.epartner.resources.ImageResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

/**
 * Created by martin on 17/10/16.
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ImageResource.class);


    @ExceptionHandler(value = EntityNotFoundException.class )
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason ="No encontrado")
    public void handle(){
        //Nothing to do
    }

    @ExceptionHandler(CategoryInUseException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Categoria en Uso")
    public void handle(CategoryInUseException ex) {
        //handle used category
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity handleStorageException(StorageException se) {
        logger.error("Error guardando imagen ", se);
        return ResponseEntity.notFound().build();
    }
}
