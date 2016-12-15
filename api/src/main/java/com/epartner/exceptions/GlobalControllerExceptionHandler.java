package com.epartner.exceptions;

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

    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(value = EntityNotFoundException.class )
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No encontrado")
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

    @ExceptionHandler(AlreadyImportedException.class)
    public ResponseEntity handleAlreadyImported(AlreadyImportedException ex) {
        logger.error("Error importando el producto", ex);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NoAvailableStockException.class)
    public ResponseEntity handleInvalidStock(NoAvailableStockException ex) {
        logger.error("Error intentando realizar la compra", ex);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(InvalidPaymentTypeException.class)
    public ResponseEntity handleInvalidStock(InvalidPaymentTypeException ex) {
        logger.error("Error intentando realizar la compra", ex);
        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler(InvalidPaymentStateException.class)
    public ResponseEntity handleInvalidState(InvalidPaymentStateException ex) {
        logger.error("Error intentando cambiar el estado", ex);
        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler(MeliNotConfiguredException.class)
    public ResponseEntity handlerError(MeliNotConfiguredException e) {
        logger.info("Mercado libre no fue configurado");
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
