package com.epartner.services;

import com.epartner.converters.ProductConverter;
import com.epartner.converters.TagConverter;
import com.epartner.domain.Product;
import com.epartner.domain.Tag;
import com.epartner.repositories.ProductRepository;
import com.epartner.repositories.TagRepository;
import com.epartner.representations.ProductRepresentation;
import com.epartner.representations.TagRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by martin on 24/09/16.
 */
@Service
public class TagService {

    private TagConverter converter;
    private TagRepository repository;
    private ProductConverter productConverter;
    private final ProductService productService;

    private final ProductRepository productRepository;

    private final Integer MAX = 10;
    private final Integer PAGE = 0;

    @Autowired
    public TagService(
        TagConverter tagConverter,
        TagRepository repository,
        ProductConverter productConverter,
        ProductService productService,
        ProductRepository productRepository){

        this.repository = repository;
        this.converter = tagConverter;
        this.productConverter = productConverter;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    public TagRepresentation create(TagRepresentation tagRepresentation) {
        return this.converter.convert(
                this.repository.save(
                    this.converter.convert(tagRepresentation)));
    }

    public TagRepresentation createTagProduct(
            Long  tagId,
            Long  productId) {

        Product product = this.productConverter.convert(
                this.productService.show(productId));

        Tag tag = this.repository.findOne(tagId);
        tag.addProduct(product);

        return this.converter.convert(this.repository.save(tag));
    }

    public Page<TagRepresentation> list(Optional<Integer> max, Optional<Integer> page) {
        Page<Tag> stored = this.repository.findAll(
                new PageRequest(page.orElse(PAGE), max.orElse(MAX))
        );

        return this.converter.convert(stored);
    }
}
