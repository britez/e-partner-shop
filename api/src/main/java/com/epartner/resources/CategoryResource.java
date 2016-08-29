package com.epartner.resources;

import com.epartner.representations.CategoryRepresentation;
import com.epartner.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

/**
 * Created by mbritez on 28/08/16.
 */
@RestController
@RequestMapping(value = CategoryResource.RESOURCE)
public class CategoryResource {

    public static final String RESOURCE = "/categories";

    private CategoryService service;

    @Autowired
    public CategoryResource(CategoryService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<CategoryRepresentation> list(
            @RequestParam Integer max,
            @RequestParam Integer page){
        return this.service.getAllCategories(
                Optional.ofNullable(max),
                Optional.ofNullable(page));
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryRepresentation create(
            @RequestBody CategoryRepresentation representation){
        return this.service.create(representation);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public CategoryRepresentation update(
            @RequestBody CategoryRepresentation representation ,
            @PathVariable Long id){
        return this.service.update(representation, id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.service.delete(id);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handle(EntityNotFoundException ex){
        //handle not found
    }
}
