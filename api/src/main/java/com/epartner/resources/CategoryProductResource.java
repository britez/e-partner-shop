package com.epartner.resources;

import com.epartner.representations.ProductRepresentation;
import com.epartner.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by mbritez on 18/09/16.
 */
@RestController
@RequestMapping(value = CategoryResource.RESOURCE + CategoryResource.ID + "/products")
public class CategoryProductResource {

    private static final String DEFAULT_MAX = "10";
    private static final String DEFAULT_PAGE = "0";
    private ProductService productService;

    @Autowired
    public CategoryProductResource(ProductService productService){
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<ProductRepresentation> list(
        @PathVariable Long id,
        @RequestParam(required = false) Boolean isPublished,
        @RequestParam(required = false, defaultValue = DEFAULT_MAX) Integer max,
        @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page){
        return this.productService.listByCategoryId(id,
                Optional.ofNullable(isPublished),
                Optional.ofNullable(max),
                Optional.ofNullable(page));
    }

}
