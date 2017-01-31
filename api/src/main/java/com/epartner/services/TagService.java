package com.epartner.services;

import com.epartner.converters.TagConverter;
import com.epartner.domain.Product;
import com.epartner.domain.Tag;
import com.epartner.exceptions.NotProductException;
import com.epartner.repositories.ProductRepository;
import com.epartner.repositories.TagRepository;
import com.epartner.representations.TagRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

/**
 * Created by martin on 24/09/16.
 */
@Service
public class TagService {

    private TagConverter converter;
    private TagRepository repository;
    private final ProductRepository productRepository;

    private final Integer MAX = 10;
    private final Integer PAGE = 0;

    @Autowired
    public TagService(
        TagConverter tagConverter,
        TagRepository repository,
        ProductRepository productRepository){

        this.repository = repository;
        this.converter = tagConverter;
        this.productRepository = productRepository;
    }

    public TagRepresentation create(TagRepresentation tagRepresentation) {

        Tag tag = new Tag();
        tag.setIsCategory(tagRepresentation.getIsCategory());
        tag.setName(tagRepresentation.getName());

        tagRepresentation.getProducts().forEach(it -> tag.addProduct(this.productRepository.findOne(it.getId())));

        this.repository.save(tag);

        return this.converter.convert(tag);
    }

    public TagRepresentation update(TagRepresentation tagRepresentation, Long tagId) {
        Tag stored = Optional.ofNullable(this.repository.findOne(tagId)).orElseThrow(EntityNotFoundException::new);
        if(tagRepresentation.getProducts().size() < 1){
            throw new NotProductException();
        }
        stored.getProducts().clear();

        tagRepresentation.getProducts().forEach(it -> {
            stored.addProduct(this.productRepository.getOne(it.getId()));
        });

        this.repository.save(stored);

        return this.converter.convert(stored);
    }

    public Page<TagRepresentation> list(
            Optional<Integer> max,
            Optional<Integer> page,
            Optional<Boolean> isCategory,
            Optional<String> query) {

        Page<Tag> stored;
        Pageable pageRequest = this.buildPageRequest(max, page);

        if(!isCategory.isPresent()) {
            stored = this.repository.findAllByNameContaining(query.orElse(""), pageRequest);
        } else {
            stored = this.repository.findAllByIsCategoryAndNameContaining(isCategory.get(), query.orElse(""), pageRequest);
        }

        return this.converter.convert(stored, pageRequest);
    }

    private PageRequest buildPageRequest(Optional<Integer> max, Optional<Integer> page){
        return new PageRequest(page.orElse(PAGE), max.orElse(MAX));
    }

    public TagRepresentation getByName(String name) {
        return this.repository.findOneByName(name)
                .map(it -> this.converter.convert(it))
                .orElse(null);
    }

    public void delete(TagRepresentation tagRepresentation) {
        Tag stored = this.show(tagRepresentation.getId());

        stored.getProducts().forEach(this::removeFromProduct);

        this.repository.delete(stored);
    }

    public TagRepresentation get(Long id) {
        return this.converter.convert(this.show(id));
    }

    private Tag show(Long id) {
        return Optional.ofNullable(
                this.repository.findOne(id))
                .orElseThrow(EntityExistsException::new);
    }

    public void removeFromProduct(Product product) {
        product.getTags().forEach(tag -> {
            tag.getProducts().remove(product);
            this.repository.delete(tag);
        });
    }
}