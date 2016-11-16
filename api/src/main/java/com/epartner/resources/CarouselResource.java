package com.epartner.resources;

import com.epartner.representations.CarouselRepresentation;
import com.epartner.services.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * Created by mapsi on 10/22/16.
 */
@RestController
@RequestMapping(value = {CarouselResource.RESOURCE, CarouselResource.PUBLIC_RESOURCE})
public class CarouselResource {

    public static final String RESOURCE = "api/admin/me/carousels";
    public static final String PUBLIC_RESOURCE = "api/carousels";
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
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(required = false) String query){
        return this.service.getAll(
                Optional.ofNullable(query),
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

    @RequestMapping(method = RequestMethod.POST, value = ID + "/background-images")
    public ResponseEntity createBackgroundImage(
            @PathVariable("id") Long id,
            @RequestParam("file") MultipartFile file){
        service.addImage(id, file, false);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = ID + "/principal-images")
    public ResponseEntity createPrincipalImage(
            @PathVariable("id") Long id,
            @RequestParam("file") MultipartFile file){
        service.addImage(id, file, true);
        return ResponseEntity.ok().build();
    }
}