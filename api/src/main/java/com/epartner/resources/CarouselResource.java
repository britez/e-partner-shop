package com.epartner.resources;

import com.epartner.representations.CarouselRepresentation;
import com.epartner.representations.CategoryRepresentation;
import com.epartner.services.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by mapsi on 10/22/16.
 */
@RestController
@RequestMapping(value = CarouselResource.RESOURCE)
public class CarouselResource {

    public static final String RESOURCE = "api/carousels";
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_MAX = "10";
    public static final String ID = "/{id}";

    private CarouselService service;

    @Autowired
    public CarouselResource(CarouselService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<CarouselRepresentation> list(
            @RequestParam(required = false, defaultValue = DEFAULT_MAX) Integer max,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page){
        return this.service.getAll(
                Optional.ofNullable(max),
                Optional.ofNullable(page));
    }

    @RequestMapping(value = ID, method = RequestMethod.GET)
    public CarouselRepresentation get(@PathVariable Long id){
        return this.service.show(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CarouselRepresentation create(
            @RequestBody CarouselRepresentation representation){
        return this.service.create(representation);
    }

    @RequestMapping(value = ID, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public CarouselRepresentation update(
            @RequestBody CarouselRepresentation representation ,
            @PathVariable Long id){
        return this.service.update(representation, id);
    }

    @RequestMapping(value = ID, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.service.delete(id);
    }

}