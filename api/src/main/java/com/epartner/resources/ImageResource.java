package com.epartner.resources;

import com.epartner.exceptions.StorageException;
import com.epartner.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by maty on 4/9/16.
 */
@RestController
@RequestMapping(value = ImageResource.IMAGES)
public class ImageResource {


    public final static String IMAGES = ProductResource.PRODUCTS + "/{productId}";
    private ProductService productService;

    @Autowired
    public ImageResource(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/images")
    public ResponseEntity create(@PathVariable("productId") Long id, @RequestParam("files") List<MultipartFile> files){
        productService.addImage(id, files);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, value= "/principal-images")
    public ResponseEntity createPrincipalImage(@PathVariable("productId") Long id, @RequestParam("file") MultipartFile file){
        productService.addPrincipalImage(id, file);
        return ResponseEntity.ok().build();
    }


}
