package com.epartner.resources;

import com.epartner.representations.TagRepresentation;
import com.epartner.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by martin on 24/09/16.
 */
@RequestMapping(value = TagResource.TAGS)
@RestController
public class TagResource {

    public static final String TAGS = "api/tags";
    public static final String PRODUCT_TAG = "/{id}/product/{productId}";
    private final TagService tagService;

    //TODO: extraer a una clase o config
    private static final String DEFAULT_PAGE = "0";
    private static final String DEFAULT_MAX = "10";

    @Autowired
    public TagResource(TagService tagService){
        this.tagService = tagService;
    }

    @RequestMapping(method =  RequestMethod.GET)
    public Page<TagRepresentation> list(@RequestParam(required = false, defaultValue = DEFAULT_MAX) Integer max,
                          @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page){
        return this.tagService.list(Optional.ofNullable(max), Optional.ofNullable(page));
    }

    @RequestMapping(method = RequestMethod.POST)
    public TagRepresentation create(@RequestBody TagRepresentation tagRepresentation){
        return tagService.create(tagRepresentation);
    }

    //TODO: Sacar deberíamos hacerlo todo junto
    @RequestMapping(value = PRODUCT_TAG,method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createTagProduct(@PathVariable("id") Long tagId, @PathVariable("productId") Long productId){
        tagService.createTagProduct(tagId, productId );
    }

    //TODO: Falta el put
}
