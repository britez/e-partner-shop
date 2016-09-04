package com.epartner.resources;

import com.epartner.representations.ProductRepresentation;
import com.epartner.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by maty on 1/9/16.
 */
@RequestMapping(value = ProductResource.PRODUCTS)
@RestController
public class ProductResource {

    public static final String PRODUCTS = "api/products";
    public static final String ID = "/{id}";
    private static final String DEFAULT_PAGE = "0";
    private static final String DEFAULT_MAX = "10";

    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ProductRepresentation create(@RequestBody ProductRepresentation productRepresentation){
        return productService.create(productRepresentation);
    }

    @RequestMapping(value = ID, method = RequestMethod.PUT)
    public ProductRepresentation update(
            @RequestBody ProductRepresentation productRepresentation,
            @PathVariable Long id){
        return productService.update(productRepresentation, id);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        productService.delete(id);
    }

    @RequestMapping(value = ID, method = RequestMethod.GET )
    public ProductRepresentation get(@PathVariable Long id){
        return productService.show(id);
    }

    @RequestMapping(method = GET)
    public Page<ProductRepresentation> list(
        @RequestParam(required = false, defaultValue = DEFAULT_MAX) Integer max,
        @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page){
        return this.productService.list(Optional.ofNullable(max), Optional.ofNullable(page));
    }
}
