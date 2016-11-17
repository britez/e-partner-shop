package com.epartner.resources;

import com.epartner.representations.ProductRepresentation;
import com.epartner.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by maty on 16/11/16.
 */
@RequestMapping(value = PublicProductsResource.PUBLIC_PRODUCTS)
@RestController
public class PublicProductsResource {


    public static final String PUBLIC_PRODUCTS = "api/products";
    public static final String ID = "/{id}";

    private static final String DEFAULT_PAGE = "0";
    private static final String DEFAULT_MAX = "10";

    private final ProductService productService;

    @Autowired
    public PublicProductsResource(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = ID, method = RequestMethod.GET )
    public ProductRepresentation get(@PathVariable Long id){
        return productService.show(id);
    }

    @RequestMapping(method = GET)
    public Page<ProductRepresentation> list(
            @RequestParam(required = false) String query,
            @RequestParam(required = false, defaultValue = DEFAULT_MAX) Integer max,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page){
        return this.productService.listPublished(
                Optional.ofNullable(query),
                Optional.ofNullable(max),
                Optional.ofNullable(page));
    }
}
