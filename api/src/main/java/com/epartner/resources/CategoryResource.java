package com.epartner.resources;

import com.epartner.representations.CategoryRepresentation;
import com.epartner.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by mbritez on 28/08/16.
 */
@RestController
@RequestMapping(value = CategoryResource.RESOURCE)
public class CategoryResource {

    public static final String RESOURCE = "api/admin/me/categories";
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_MAX = "10";
    public static final String ID = "/{id}";

    private CategoryService service;

    @Autowired
    public CategoryResource(CategoryService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<CategoryRepresentation> list(
            @RequestParam(required = false, defaultValue = DEFAULT_MAX) Integer max,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page){
        return this.service.getAllCategories(
                Optional.ofNullable(max),
                Optional.ofNullable(page));
    }

    @RequestMapping(value = ID, method = RequestMethod.GET)
    public CategoryRepresentation get(@PathVariable Long id){
        return this.service.show(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryRepresentation create(
            @RequestBody CategoryRepresentation representation){
        return this.service.create(representation);
    }

    @RequestMapping(value = ID, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public CategoryRepresentation update(
            @RequestBody CategoryRepresentation representation ,
            @PathVariable Long id){
        return this.service.update(representation, id);
    }

    @RequestMapping(value = ID, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.service.delete(id);
    }



}
