package com.epartner.resources;

import com.epartner.exceptions.StorageException;
import com.epartner.services.ProductService;
import com.epartner.services.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by maty on 4/9/16.
 */
@RestController
@RequestMapping(value = ImageResource.IMAGES)
public class ImageResource {

    private static final Logger logger = LoggerFactory.getLogger(ImageResource.class);

    public final static String IMAGES = ProductResource.PRODUCTS + "/{productId}/images";
    private ProductService productService;


    @Autowired
    public ImageResource(ProductService productService) {

        this.productService = productService;
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity handleStorageException(StorageException se) {

        logger.error("Error guardando imagen ", se);
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@PathVariable("productId") Long id, @RequestParam("file") MultipartFile file){

        productService.addImagen(id, file);

        return ResponseEntity.ok().build();
    }
}
