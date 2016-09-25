package com.epartner.resources;

import com.epartner.domain.Tag;
import com.epartner.representations.ProductRepresentation;
import com.epartner.representations.TagRepresentation;
import com.epartner.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.epartner.resources.TagResource.PRODUCT_TAG;

/**
 * Created by martin on 24/09/16.
 */
@RequestMapping(value = TagResource.TAGS)
@RestController
public class TagResource {

    public static final String TAGS = "api/tags";
    public static final String PRODUCT_TAG = "/{id}/product/{productId}";
    private final TagService tagService;


    @Autowired
    public TagResource(TagService tagService){

        this.tagService = tagService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public TagRepresentation create(@RequestBody TagRepresentation tagRepresentation){
        return tagService.create(tagRepresentation);
    }

    @RequestMapping(value = PRODUCT_TAG,method = RequestMethod.POST)

    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createTagProduct(@PathVariable("id") Long tagId, @PathVariable("productId") Long productId){

        tagService.createTagProduct(tagId, productId );
    }




}
