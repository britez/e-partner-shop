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
    private final TagService tagService;

    //TODO: extraer a una clase o config
    private static final String DEFAULT_PAGE = "0";
    private static final String DEFAULT_MAX = "10";

    @Autowired
    public TagResource(TagService tagService){
        this.tagService = tagService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public TagRepresentation get(@PathVariable("id") Long id){
        return this.tagService.get(id);
    }

    @RequestMapping(method =  RequestMethod.GET)
    public Page<TagRepresentation> list(
                @RequestParam(required = false) Boolean isCategory,
                @RequestParam(required = false, defaultValue = DEFAULT_MAX) Integer max,
                @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page){
        return this.tagService.list(Optional.ofNullable(max), Optional.ofNullable(page), Optional.ofNullable(isCategory));
    }

    @RequestMapping(method = RequestMethod.POST)
    public TagRepresentation create(@RequestBody TagRepresentation tagRepresentation){
        return tagService.create(tagRepresentation);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public TagRepresentation update(@RequestBody TagRepresentation tagRepresentation, @PathVariable("id") Long tagId) {
        return this.tagService.update(tagRepresentation, tagId);
    }

}
