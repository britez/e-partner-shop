package com.epartner.resources;

import com.epartner.exceptions.CodeNotPresentException;
import com.epartner.representations.ProductRepresentation;
import com.epartner.services.ProductImportService;
import com.epartner.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by maty on 1/9/16.
 */
@RequestMapping(value = ProductImportResource.PRODUCTS)
@RestController
public class ProductImportResource {

    public static final String PRODUCTS = "api/admin/me/product-imports";

    private static final String DEFAULT_PAGE = "0";
    private static final String DEFAULT_MAX = "10";

    private final ProductImportService productImportService;
    private final ProductService productService;

    @Autowired
    public ProductImportResource(ProductImportService productImportService,
                                 ProductService productService) {
        this.productImportService = productImportService;
        this.productService = productService;
    }

    @RequestMapping(method = GET)
    public Page<ProductRepresentation> list(
        @RequestParam(required = false, defaultValue = DEFAULT_MAX) Integer max,
        @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page,
        @RequestParam(required = false) String query){
        return this.productImportService.list(
                Optional.ofNullable(query),
                Optional.ofNullable(max),
                Optional.ofNullable(page));
    }

    @RequestMapping(method = POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void list(
            @RequestBody List<ProductRepresentation> products,
            @RequestParam Long categoryId){
        this.productService.create(categoryId, products);
    }

    @RequestMapping(method = POST, value = "/current/config")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void saveConfig(@RequestBody Map<String, String> body){
        this.productImportService.saveConfig(
                Optional.ofNullable(body.get("code"))
                        .orElseThrow(CodeNotPresentException::new));
    }
}
