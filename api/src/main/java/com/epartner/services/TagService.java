package com.epartner.services;

import com.epartner.converters.ProductConverter;
import com.epartner.converters.TagConverter;
import com.epartner.domain.Product;
import com.epartner.domain.Tag;
import com.epartner.repositories.TagRepository;
import com.epartner.representations.ProductRepresentation;
import com.epartner.representations.TagRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by martin on 24/09/16.
 */
@Service
public class TagService {

    private TagConverter tagConverter;
    private TagRepository repository;
    private ProductConverter productConverter;
    private final ProductService productService;


    @Autowired
    public TagService(
        TagConverter tagConverter,
        TagRepository repository,
        ProductConverter productConverter,
        ProductService productService){

        this.repository = repository;
        this.tagConverter = tagConverter;
        this.productConverter = productConverter;
        this.productService = productService;
    }

    public TagRepresentation create(TagRepresentation tagRepresentation) {
        return this.tagConverter.convert(
                this.repository.save(
                    this.tagConverter.convert(tagRepresentation)));
    }

    public TagRepresentation createTagProduct(
            Long  tagId,
            Long  productId) {
        Product product = this.productConverter.convert(
                this.productService.show(productId));

        Tag tag = this.repository.findOne(tagId);
        tag.addProduct(product);

        return this.tagConverter.convert(this.repository.save(tag));
    }
}
